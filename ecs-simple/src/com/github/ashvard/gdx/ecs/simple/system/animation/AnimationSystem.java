package com.github.ashvard.gdx.ecs.simple.system.animation;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;
import com.github.ashvard.gdx.ecs.simple.engine.EcsEntity;
import com.github.ashvard.gdx.ecs.simple.engine.EcsSystem;
import com.github.ashvard.gdx.ecs.simple.system.render.RendererComponent;
import com.github.ashvard.gdx.simple.animation.SimpleAnimation;
import com.github.ashvard.gdx.simple.animation.SimpleAnimationSystem;

//TODO вынести класс выше из ecs, здесь ему не место
/**
 * Created by user on 09.04.2017.
 */
public class AnimationSystem extends EcsSystem implements Disposable {

    private final SimpleAnimationSystem simpleAnimationSystem = new SimpleAnimationSystem();

    public AnimationSystem() {
        super(RendererComponent.class, AnimationComponent.class);
    }

    @Override
    public void update(float delta, IntMap.Values<EcsEntity> entities) {
        for (EcsEntity entity : entities) {
            AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
            simpleAnimationSystem.update(delta, animationComponent.simpleAnimationComponent);
            entity.getComponent(RendererComponent.class).textureRegion =
                    animationComponent.simpleAnimationComponent.animatorDynamicPart.currentFrame;
        }
    }

    public void addAnimation(SimpleAnimation simpleAnimation) {
        simpleAnimationSystem.addAnimation(simpleAnimation);
    }

    public void unload(String title) {
        simpleAnimationSystem.unload(title);
    }

    public void dispose() {
        simpleAnimationSystem.dispose();
    }

}
