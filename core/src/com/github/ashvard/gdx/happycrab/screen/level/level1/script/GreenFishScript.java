package com.github.ashvard.gdx.happycrab.screen.level.level1.script;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.github.ashvard.gdx.happycrab.model.scripts.AbstractGameObjectScript;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.components.AnimationComponent;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.components.DamageComponent;
import com.github.ashvard.gdx.simple.animation.component.AnimatorDynamicPart;
import com.github.ashvard.gdx.simple.animation.component.SimpleAnimationComponent;
import com.github.ashvard.gdx.simple.animation.component.SimpleAnimatorUtils;
import com.github.ashvard.gdx.simple.animation.fsm.FsmContext;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.utils.ComponentRetriever;

public class GreenFishScript extends AbstractGameObjectScript {

    protected ComponentMapper<PhysicsBodyComponent> physicsMapper;
    protected ComponentMapper<AnimationComponent> animationMapper;

    private PhysicsBodyComponent physicsBodyComponent;
    private AnimationComponent animationComponent;

    private float normalStateTime;
    private float dangerousStateTime;

    private final byte normalState = 0;
    private final byte dangerousState = 1;
    private byte currentState;

    @Override
    public void init(int item) {
        super.init(item);
        physicsBodyComponent = physicsMapper.get(item);
        animationComponent = animationMapper.get(item);
        reinitStates();
    }

    @Override
    public void act(float delta) {
        SimpleAnimationComponent simpleAnimationComponent = animationComponent.simpleAnimationComponent;
        FsmContext fsmContext = simpleAnimationComponent.fsmContext;
        Vector2 linearVelocity = physicsBodyComponent.body.getLinearVelocity();

        changeState(delta, fsmContext, linearVelocity, simpleAnimationComponent.animatorDynamicPart);
        switch (currentState) {
            case normalState:
                ComponentRetriever.remove(getEntity(), DamageComponent.class, getEngine());
                break;
            case dangerousState:
                DamageComponent damageComponent = ComponentRetriever.create(getEntity(), DamageComponent.class, getEngine());
                damageComponent.damageValue = 1;
                damageComponent.isBounceAllowed = true;
                break;
        }
    }

    @Override
    public void beginContact(int i, Fixture fixture, Fixture fixture1, Contact contact) {

    }

    @Override
    public void endContact(int i, Fixture fixture, Fixture fixture1, Contact contact) {

    }

    @Override
    public void preSolve(int i, Fixture fixture, Fixture fixture1, Contact contact) {

    }

    @Override
    public void postSolve(int i, Fixture fixture, Fixture fixture1, Contact contact) {

    }

    @Override
    public void dispose() {

    }

    private void changeState(float delta, FsmContext fsmContext, Vector2 linearVelocity, AnimatorDynamicPart animatorDynamicPart) {
        boolean isIdle = fsmContext.get(GreenFishAnim.Transition.IS_IDLE) != null && (Boolean) fsmContext.get(GreenFishAnim.Transition.IS_IDLE);
        boolean isSwimming = fsmContext.get(GreenFishAnim.Transition.IS_SWIMMING) != null && (Boolean) fsmContext.get(GreenFishAnim.Transition.IS_SWIMMING);
        boolean isDeath = fsmContext.get(GreenFishAnim.Transition.IS_DEATH) != null && (Boolean) fsmContext.get(GreenFishAnim.Transition.IS_DEATH);
        boolean isToSpike = fsmContext.get(GreenFishAnim.Transition.IS_TO_SPIKE) != null && (Boolean) fsmContext.get(GreenFishAnim.Transition.IS_TO_SPIKE);
        boolean isToSpikeIdle = fsmContext.get(GreenFishAnim.Transition.IS_TO_SPIKE_IDLE) != null && (Boolean) fsmContext.get(GreenFishAnim.Transition.IS_TO_SPIKE_IDLE);
        boolean isToSpikeFrom = fsmContext.get(GreenFishAnim.Transition.IS_TO_SPIKE_FROM) != null && (Boolean) fsmContext.get(GreenFishAnim.Transition.IS_TO_SPIKE_FROM);

        if (normalStateTime > 0) {
            normalStateTime -= delta;

            if (Math.abs(linearVelocity.x) > 0) {
                fsmContext.insert(GreenFishAnim.Transition.IS_IDLE, true);
                fsmContext.insert(GreenFishAnim.Transition.IS_SWIMMING, false);
            } else if (Math.abs(linearVelocity.y) > 0) {
                fsmContext.insert(GreenFishAnim.Transition.IS_SWIMMING, true);
                fsmContext.insert(GreenFishAnim.Transition.IS_IDLE, false);
            }
        } else if (dangerousStateTime > 0) {
            dangerousStateTime -= delta;

            fsmContext.insert(GreenFishAnim.Transition.IS_IDLE, false);
            fsmContext.insert(GreenFishAnim.Transition.IS_SWIMMING, false);
            fsmContext.insert(GreenFishAnim.Transition.IS_TO_SPIKE, true);

            if (isToSpike) {
                if (SimpleAnimatorUtils.isAnimationFinished(animatorDynamicPart)) {
                    // шипы пошли, опасное время!
                    currentState = dangerousState;
                    fsmContext.insert(GreenFishAnim.Transition.IS_TO_SPIKE, false);
                    fsmContext.insert(GreenFishAnim.Transition.IS_TO_SPIKE_IDLE, true);
                }
            }

        } else if (dangerousStateTime <= 0) {
            // убираем шипы
            fsmContext.insert(GreenFishAnim.Transition.IS_TO_SPIKE_IDLE, false);
            fsmContext.insert(GreenFishAnim.Transition.IS_TO_SPIKE_FROM, true);

            if (isToSpikeFrom && SimpleAnimatorUtils.isAnimationFinished(animatorDynamicPart)) {
                // шипы убраны, можно возвращаться в нормальное состояние
                reinitStates();
            }
        }
    }

    private void reinitStates() {
        currentState = normalState;
        normalStateTime = 3.0f + MathUtils.random() / 3f * 5f;
        dangerousStateTime = 3.0f + MathUtils.random() / 3f * 5f;
        reinitFsmContext();
    }

    private void reinitFsmContext() {
        SimpleAnimationComponent simpleAnimationComponent = animationComponent.simpleAnimationComponent;
        FsmContext fsmContext = simpleAnimationComponent.fsmContext;
        fsmContext.insert(GreenFishAnim.Transition.IS_IDLE, true);
        fsmContext.insert(GreenFishAnim.Transition.IS_SWIMMING, false);
        fsmContext.insert(GreenFishAnim.Transition.IS_TO_SPIKE, false);
        fsmContext.insert(GreenFishAnim.Transition.IS_TO_SPIKE_IDLE, false);
        fsmContext.insert(GreenFishAnim.Transition.IS_TO_SPIKE_FROM, false);
        fsmContext.insert(GreenFishAnim.Transition.IS_DEATH, false);
    }

}
