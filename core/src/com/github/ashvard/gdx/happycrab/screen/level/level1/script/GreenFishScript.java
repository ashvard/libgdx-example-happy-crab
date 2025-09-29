package com.github.ashvard.gdx.happycrab.screen.level.level1.script;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.components.AnimationComponent;
import com.github.ashvard.gdx.simple.animation.component.AnimatorDynamicPart;
import com.github.ashvard.gdx.simple.animation.component.SimpleAnimationComponent;
import com.github.ashvard.gdx.simple.animation.component.SimpleAnimatorUtils;
import com.github.ashvard.gdx.simple.animation.fsm.FsmContext;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.scripts.BasicScript;

public class GreenFishScript extends BasicScript implements PhysicsContact {

    protected ComponentMapper<PhysicsBodyComponent> physicsMapper;
    protected ComponentMapper<AnimationComponent> animationMapper;

    private PhysicsBodyComponent physicsBodyComponent;
    private AnimationComponent animationComponent;

    private float normalState;
    private float dangerousState;

    @Override
    public void init(int item) {
        super.init(item);
        physicsBodyComponent = physicsMapper.get(item);
        animationComponent = animationMapper.get(item);
        reinitStates();
    }

    private void reinitStates() {
        normalState = 3.0f + MathUtils.random() / 3f * 10f;
        dangerousState = 3.0f + MathUtils.random() / 3f * 10f;

        SimpleAnimationComponent simpleAnimationComponent = animationComponent.simpleAnimationComponent;
        FsmContext fsmContext = simpleAnimationComponent.fsmContext;
        fsmContext.insert(GreenFishAnim.IS_IDLE, true);
        fsmContext.insert(GreenFishAnim.IS_SWIMMING, false);
        fsmContext.insert(GreenFishAnim.IS_TO_SPIKE, false);
        fsmContext.insert(GreenFishAnim.IS_TO_SPIKE_IDLE, false);
        fsmContext.insert(GreenFishAnim.IS_TO_SPIKE_FROM, false);
        fsmContext.insert(GreenFishAnim.IS_DEATH, false);
    }

    @Override
    public void act(float delta) {
        SimpleAnimationComponent simpleAnimationComponent = animationComponent.simpleAnimationComponent;
        FsmContext fsmContext = simpleAnimationComponent.fsmContext;
        Vector2 linearVelocity = physicsBodyComponent.body.getLinearVelocity();

        boolean isIdle = fsmContext.get(GreenFishAnim.IS_IDLE) != null && (Boolean) fsmContext.get(GreenFishAnim.IS_IDLE);
        boolean isSwimming = fsmContext.get(GreenFishAnim.IS_SWIMMING) != null && (Boolean) fsmContext.get(GreenFishAnim.IS_SWIMMING);
        boolean isDeath = fsmContext.get(GreenFishAnim.IS_DEATH) != null && (Boolean) fsmContext.get(GreenFishAnim.IS_DEATH);
        boolean isToSpike = fsmContext.get(GreenFishAnim.IS_TO_SPIKE) != null && (Boolean) fsmContext.get(GreenFishAnim.IS_TO_SPIKE);
        boolean isToSpikeIdle = fsmContext.get(GreenFishAnim.IS_TO_SPIKE_IDLE) != null && (Boolean) fsmContext.get(GreenFishAnim.IS_TO_SPIKE_IDLE);
        boolean isToSpikeFrom = fsmContext.get(GreenFishAnim.IS_TO_SPIKE_FROM) != null && (Boolean) fsmContext.get(GreenFishAnim.IS_TO_SPIKE_FROM);

        if (normalState > 0) {
            normalState -= delta;

            if (Math.abs(linearVelocity.x) > 0) {
                fsmContext.insert(GreenFishAnim.IS_IDLE, true);
                fsmContext.insert(GreenFishAnim.IS_SWIMMING, false);
            } else if (Math.abs(linearVelocity.y) > 0) {
                fsmContext.insert(GreenFishAnim.IS_SWIMMING, true);
                fsmContext.insert(GreenFishAnim.IS_IDLE, false);
            }
        } else if (dangerousState > 0) {
            dangerousState -= delta;

            fsmContext.insert(GreenFishAnim.IS_IDLE, false);
            fsmContext.insert(GreenFishAnim.IS_SWIMMING, false);
            fsmContext.insert(GreenFishAnim.IS_TO_SPIKE, true);

            if (isToSpike && SimpleAnimatorUtils.isAnimationFinished(simpleAnimationComponent.animatorDynamicPart)) {
                // шипы пошли, опасное время!
                fsmContext.insert(GreenFishAnim.IS_TO_SPIKE, false);
                fsmContext.insert(GreenFishAnim.IS_TO_SPIKE_IDLE, true);
            }
        } else if (dangerousState <= 0) {
            // убираем шипы
            fsmContext.insert(GreenFishAnim.IS_TO_SPIKE_IDLE, false);
            fsmContext.insert(GreenFishAnim.IS_TO_SPIKE_FROM, true);

            if (isToSpikeFrom && SimpleAnimatorUtils.isAnimationFinished(simpleAnimationComponent.animatorDynamicPart)) {
                // шипы убраны, можно возвращаться в нормальное состояние
                reinitStates();
            }
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

}
