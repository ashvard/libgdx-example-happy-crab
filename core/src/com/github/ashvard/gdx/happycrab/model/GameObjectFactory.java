package com.github.ashvard.gdx.happycrab.model;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.ashvard.gdx.ecs.simple.engine.EcsComponent;
import com.github.ashvard.gdx.ecs.simple.engine.EcsScript;
import com.github.ashvard.gdx.ecs.simple.system.animation.AnimationComponent;
import com.github.ashvard.gdx.ecs.simple.system.render.RendererComponent;
import com.github.ashvard.gdx.ecs.simple.system.script.ScriptComponent;
import com.github.ashvard.gdx.ecs.simple.system.transform.TransformComponent;
import com.github.ashvard.gdx.happycrab.Resources;
import com.github.ashvard.gdx.happycrab.model.scripts.CrabAnimScript;
import com.github.ashvard.gdx.happycrab.model.scripts.CrabScript;
import com.github.ashvard.gdx.happycrab.screen.level.level0.physics.InputComponent;
import com.github.ashvard.gdx.happycrab.screen.level.level0.physics.PhysicsComponent;
import com.github.ashvard.gdx.simple.animation.component.AnimatorDynamicPart;
import com.github.ashvard.gdx.simple.animation.component.SimpleAnimationComponent;
import com.github.ashvard.gdx.simple.animation.fsm.FsmContext;

import com.github.ashvard.gdx.happycrab.screen.level.level1.script.GreenFishAnim;


public final class GameObjectFactory {

    private GameObjectFactory() {
    }

    public static EcsComponent[] createBackground(TextureRegion region) {
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position = new Vector2(0f, 0f);
        transformComponent.rotation = 0f;

        RendererComponent rendererComponent = new RendererComponent();
        rendererComponent.textureRegion = region;
        rendererComponent.layer = LayerEnum.BACKGROUND.name();

        return new EcsComponent[]{transformComponent, rendererComponent};
    }

    public static EcsComponent[] createCrab(InputComponent inputComponent) {
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position = new Vector2(250, 250);
        transformComponent.rotation = 0f;

        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.velocity = new Vector2(0f, 0f);
        physicsComponent.shape = new Rectangle(0, 0, 75, 64);

        ScriptComponent scriptComponent = new ScriptComponent();
        scriptComponent.scripts = new Array<EcsScript>(new EcsScript[]{new CrabScript(), new CrabAnimScript()});

        RendererComponent rendererComponent = new RendererComponent();
        //rendererComponent.textureRegion = crabAtlas.findRegion("crab", 1);
        rendererComponent.layer = LayerEnum.MAIN_LAYER.name();

        // АНИМАЦИЯ
        AnimationComponent animationComponent = new AnimationComponent();
        animationComponent.simpleAnimationComponent = createAnimationComponentCrab();
        // АНИМАЦИЯ

        return new EcsComponent[]{transformComponent, physicsComponent, scriptComponent, rendererComponent, animationComponent, inputComponent};
    }

    public static SimpleAnimationComponent createAnimationComponentCrab() {
        FsmContext fsmContext = new FsmContext();
        fsmContext.insert(CrabAnimScript.MOVEMENT, 0f);
        fsmContext.insert(CrabAnimScript.ATTACK, false);
        fsmContext.insert(CrabAnimScript.JUMP, false);
        fsmContext.insert(CrabAnimScript.FLY, false);

        AnimatorDynamicPart animatorDynamicPart = new AnimatorDynamicPart(/*animatorIdle*/);
        return new SimpleAnimationComponent(Resources.Animations.CRAB, fsmContext, animatorDynamicPart);
    }

    public static SimpleAnimationComponent createAnimationComponentGreenFish() {
        FsmContext fsmContext = new FsmContext();
        fsmContext.insert(GreenFishAnim.IS_IDLE, true);
        fsmContext.insert(GreenFishAnim.IS_DEATH, false);
        fsmContext.insert(GreenFishAnim.IS_SWIMMING, false);
        fsmContext.insert(GreenFishAnim.IS_TO_SPIKE, false);
        fsmContext.insert(GreenFishAnim.IS_TO_SPIKE_IDLE, false);

        AnimatorDynamicPart animatorDynamicPart = new AnimatorDynamicPart(/*animatorIdle*/);
        return new SimpleAnimationComponent(Resources.Animations.GREEN_FISH, fsmContext, animatorDynamicPart);
    }

}
