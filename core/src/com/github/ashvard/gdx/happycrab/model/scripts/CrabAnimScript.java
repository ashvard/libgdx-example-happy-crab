package com.github.ashvard.gdx.happycrab.model.scripts;

import com.github.ashvard.gdx.ecs.simple.engine.EcsScript;
import com.github.ashvard.gdx.ecs.simple.system.animation.AnimationComponent;
import com.github.ashvard.gdx.ecs.simple.system.render.RendererComponent;
import com.github.ashvard.gdx.ecs.simple.system.transform.TransformComponent;
import com.github.ashvard.gdx.happycrab.action.HeroActions;
import com.github.ashvard.gdx.happycrab.screen.level.level.physics.InputComponent;
import com.github.ashvard.gdx.happycrab.screen.level.level.physics.PhysicsComponent;
import com.github.ashvard.gdx.simple.animation.component.SimpleAnimatorUtils;
import com.github.ashvard.gdx.simple.animation.fsm.FsmContext;

/**
 * Created by user on 17.04.2017.
 */
public class CrabAnimScript extends EcsScript {

    public static final String IDLE = "IDLE";
    public static final String JUMP = "JUMP";
    public static final String MOVEMENT = "MOVEMENT";
    public static final String ATTACK = "ATTACK";
    public static final String FLY = "FLY";

    private TransformComponent transform;
    private PhysicsComponent physics;
    private RendererComponent renderer;
    private AnimationComponent animation;
    private InputComponent userInput;

    private boolean attack = false;
    private boolean jump = false;

    @Override
    public void init() {
        transform = getComponent(TransformComponent.class);
        physics = getComponent(PhysicsComponent.class);
        renderer = getComponent(RendererComponent.class);
        animation = getComponent(AnimationComponent.class);
        userInput = getComponent(InputComponent.class);
    }

    @Override
    public void dispose() {
        physics = null;
        renderer = null;
        animation = null;
        userInput = null;
    }

    @Override
    public void update(float delta) {
        FsmContext context = animation.simpleAnimationComponent.fsmContext;
        if (userInput.inputActions.getAction(HeroActions.ATTACK) && !attack) {
            attack = true;
            context.update(ATTACK, true);
        }
        if (userInput.inputActions.getAction(HeroActions.JUMP) && !jump) {
            jump = true;
            context.update(JUMP, true);
        }

        if (physics.velocity.x < 0) {
            renderer.flipX = true;
        } else {
            renderer.flipX = false;
        }
        context.update(MOVEMENT, Math.abs(physics.velocity.x));

        if (transform.position.y > 0) {
            context.update(FLY, true);
        } else if (transform.position.y == 0) {
            context.update(FLY, false);
        }

        if (ATTACK.equals(context.getCurrentState())) {
            if (SimpleAnimatorUtils.isAnimationFinished(animation.simpleAnimationComponent.animatorDynamicPart)) {
                attack = false;
                context.update(ATTACK, false);
            }
        }
        if (JUMP.equals(context.getCurrentState())) {
            if (SimpleAnimatorUtils.isAnimationFinished(animation.simpleAnimationComponent.animatorDynamicPart)) {
                jump = false;
                context.update(JUMP, false);
                context.update(FLY, true);
            }
        }
    }
}
