package com.github.ashvard.gdx.happycrab.screen.level.level1.script;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.github.ashvard.gdx.happycrab.action.HeroActions;
import com.github.ashvard.gdx.happycrab.model.scripts.AbstractGameObjectScript;
import com.github.ashvard.gdx.happycrab.screen.CrabAnimationState;
import com.github.ashvard.gdx.happycrab.screen.level.Tags;
import com.github.ashvard.gdx.happycrab.screen.level.level1.Ids;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.components.*;
import com.github.ashvard.gdx.simple.animation.component.SimpleAnimatorUtils;
import com.github.ashvard.gdx.simple.animation.fsm.FsmContext;
import com.github.ashvard.gdx.simple.input.InputActions;
import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.MainItemComponent;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.utils.ItemWrapper;

import java.util.HashSet;
import java.util.Set;

public class HeroScript extends AbstractGameObjectScript {

    protected ComponentMapper<PhysicsBodyComponent> physicsMapper;
    protected ComponentMapper<TransformComponent> transformMapper;
    protected ComponentMapper<MainItemComponent> mainItemMapper;
    protected ComponentMapper<DimensionsComponent> dimensionsMapper;

    protected ComponentMapper<InputComponent> inputMapper;
    protected ComponentMapper<AnimationComponent> animationMapper;

    protected ComponentMapper<SeaShellComponent> seaShellMapper;
    protected ComponentMapper<PearlComponent> pearlMapper;
    protected ComponentMapper<HeroComponent> heroMapper;
    protected ComponentMapper<DamageComponent> damageMapper;

    protected com.artemis.World engine;

    private PhysicsBodyComponent physicsBodyComponent;

    private InputComponent inputComponent;
    private AnimationComponent animationComponent;

    private final Vector2 impulse = new Vector2(0, 0);
    private final Vector2 speed = new Vector2(0, 0);

    private boolean attack = false;

    // jump
    private int jumpTimeout = 0;
    private final Set<Fixture> fixturesUnderfoot = new HashSet<>();

    private static final int EMPTY_LAST_CONTACT_ENTITY = -1;
    private int lastContactEntity = EMPTY_LAST_CONTACT_ENTITY;


    @Override
    public void init(int item) {
        super.init(item);

        physicsBodyComponent = physicsMapper.get(item);
        inputComponent = inputMapper.get(item);
        animationComponent = animationMapper.get(item);
    }

    @Override
    public void act(float delta) {
        Vector2 linearVelocity = physicsBodyComponent.body.getLinearVelocity();
        FsmContext animationContext = animationComponent.simpleAnimationComponent.fsmContext;
        InputActions inputActions = inputComponent.inputActions;

        if (inputActions.getAction(HeroActions.MOVE_LEFT)) {
            movePlayer(HeroActions.MOVE_LEFT);
        }

        if (inputActions.getAction(HeroActions.MOVE_RIGHT)) {
            movePlayer(HeroActions.MOVE_RIGHT);
        }
        animationContext.update(CrabAnimationState.MOVEMENT, Math.abs(linearVelocity.x));

        if (inputActions.getAction(HeroActions.JUMP) && isCanJumpNow()) {
            jumpTimeout = 15; // 15 frames
            movePlayer(HeroActions.JUMP);
            animationContext.update(CrabAnimationState.JUMP, true);
        }

        if (linearVelocity.y < -0.3) {
            animationContext.update(CrabAnimationState.FLY, true);
        } else if (isCanJumpNow()) {
            animationContext.update(CrabAnimationState.FLY, false);
        }

        if (inputActions.getAction(HeroActions.ATTACK) && !attack) {
            attack = true;
            animationContext.update(CrabAnimationState.ATTACK, true);
            //TODO
        }

        if (CrabAnimationState.ATTACK.equals(animationContext.getCurrentState())) {
            if (SimpleAnimatorUtils.isAnimationFinished(animationComponent.simpleAnimationComponent.animatorDynamicPart)) {
                attack = false;
                animationContext.update(CrabAnimationState.ATTACK, false);
            }
        }
        if (CrabAnimationState.JUMP.equals(animationContext.getCurrentState())) {
            if (SimpleAnimatorUtils.isAnimationFinished(animationComponent.simpleAnimationComponent.animatorDynamicPart)) {
                animationContext.update(CrabAnimationState.JUMP, false);
                animationContext.update(CrabAnimationState.FLY, true);
            }
        }

        if (jumpTimeout > 0) {
            jumpTimeout--;
        }

        if (lastContactEntity != EMPTY_LAST_CONTACT_ENTITY) {
            DamageComponent damageComponent = damageMapper.get(lastContactEntity);
            if (damageComponent != null) {
                handleHeroDamage(lastContactEntity, heroMapper.get(getEntity()));
            }
        }
    }

    public void movePlayer(int direction) {
        Body body = physicsBodyComponent.body;

        speed.set(body.getLinearVelocity());

        switch (direction) {
            case HeroActions.MOVE_LEFT:
                impulse.set(-2f, speed.y);
                break;
            case HeroActions.MOVE_RIGHT:
                impulse.set(2f, speed.y);
                break;
            case HeroActions.JUMP:
                impulse.set(speed.x, 4.5f);
                break;
        }

        body.applyLinearImpulse(impulse.sub(speed), body.getWorldCenter(), true);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void beginContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
        lastContactEntity = contactEntity;
        HeroComponent heroComponent = heroMapper.get(entity);
        MainItemComponent mainItemComponent = mainItemMapper.get(contactEntity);

        handlePlatformContact(contactFixture, mainItemComponent);
        handleSeaShellContact(contactEntity, heroComponent);
        handlePearlContact(contactEntity, heroComponent);
        handleHeroDamage(contactEntity, heroComponent);
    }

    private void handleHeroDamage(int contactEntity, HeroComponent heroComponent) {
        DamageComponent damageComponent = damageMapper.get(contactEntity);
        if (damageComponent != null) {
            heroComponent.setLifeCount(heroComponent.getLifeCount() - damageComponent.damageValue);
            if (damageComponent.isBounceAllowed) {
                applyBounce(contactEntity, damageComponent.bouncePower);
                //TODO добавить временную неуязвимость
            }
        }
    }

    private void handlePlatformContact(Fixture contactFixture, MainItemComponent mainItemComponent) {
        if (mainItemComponent.tags.contains(Tags.PLATFORM)) {
            fixturesUnderfoot.add(contactFixture);
        }
    }

    private void handlePearlContact(int contactEntity, HeroComponent heroComponent) {
        PearlComponent pearlComponent = pearlMapper.get(contactEntity);
        if (pearlComponent != null) {
            heroComponent.setPearlsCount(heroComponent.getPearlsCount() + 1);
            engine.delete(contactEntity);
        }
    }

    private void handleSeaShellContact(int contactEntity, HeroComponent heroComponent) {
        SeaShellComponent seaShellComponent = seaShellMapper.get(contactEntity);
        if (seaShellComponent != null && seaShellComponent.pearlsCount > 0) {
            ItemWrapper itemWrapper = new ItemWrapper(contactEntity, engine);
            ItemWrapper child = itemWrapper.getChild(Ids.PEARL_ID);
            if (child != null) {
                heroComponent.setPearlsCount(heroComponent.getPearlsCount() + seaShellComponent.pearlsCount);
                seaShellComponent.pearlsCount--;
                engine.delete(child.getEntity());
            }
        }
    }

    @Override
    public void endContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
        MainItemComponent mainItemComponent = mainItemMapper.get(contactEntity);

        if (mainItemComponent != null && mainItemComponent.tags.contains(Tags.PLATFORM)) {
            fixturesUnderfoot.remove(contactFixture);
        }

        lastContactEntity = EMPTY_LAST_CONTACT_ENTITY;
    }

    @Override
    public void preSolve(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
    }

    @Override
    public void postSolve(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
    }

    private boolean isCanJumpNow() {
        if (jumpTimeout > 0) {
            return false;
        }

        return !fixturesUnderfoot.isEmpty();
    }

    private void applyBounce(int contactEntity, float bouncePower) {
        Body heroBody = physicsBodyComponent.body;

        // Получаем компоненты платформы
        TransformComponent platformTransform = transformMapper.get(contactEntity);
        DimensionsComponent platformDimensions = dimensionsMapper.get(contactEntity);

        Vector2 heroPosition = heroBody.getPosition();
        Vector2 heroVelocity = heroBody.getLinearVelocity();

        // Вычисляем центр платформы
        Vector2 platformCenter = new Vector2(
                platformTransform.x + platformDimensions.width / 2,
                platformTransform.y + platformDimensions.height / 2
        );

        // Направление отскока - от центра платформы
        Vector2 bounceDirection = new Vector2(heroPosition).sub(platformCenter).nor();
        // Учитываем текущую скорость героя (отражаем ее)
        Vector2 reflectedVelocity = new Vector2(heroVelocity).scl(-1.2f);
        // Основная сила отскока от центра платформы
        Vector2 bounceForce = bounceDirection.scl(bouncePower);
        // Комбинируем отраженную скорость и силу отскока
        Vector2 totalImpulse = reflectedVelocity.add(bounceForce);
        // Применяем импульс к герою
        heroBody.applyLinearImpulse(totalImpulse, heroBody.getWorldCenter(), true);
    }

}
