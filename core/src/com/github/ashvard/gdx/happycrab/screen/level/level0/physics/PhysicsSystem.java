package com.github.ashvard.gdx.happycrab.screen.level.level0.physics;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.IntMap;
import com.github.ashvard.gdx.ecs.simple.engine.EcsEntity;
import com.github.ashvard.gdx.ecs.simple.engine.debug.DebugDataContainer;
import com.github.ashvard.gdx.ecs.simple.engine.debug.DebugSystem;
import com.github.ashvard.gdx.ecs.simple.engine.debug.data.CircleData;
import com.github.ashvard.gdx.ecs.simple.engine.debug.data.DebugData;
import com.github.ashvard.gdx.ecs.simple.engine.debug.data.PointData;
import com.github.ashvard.gdx.ecs.simple.engine.debug.data.RectangleData;
import com.github.ashvard.gdx.ecs.simple.system.transform.TransformComponent;

public class PhysicsSystem extends DebugSystem {

    private final Vector2 gravity = new Vector2(0, -4f);

    public PhysicsSystem() {
        super(TransformComponent.class, PhysicsComponent.class);
    }

    // подумать, может эти батчи через прокси сделать, как транзакции
    @Override
    public void update(float delta, IntMap.Values<EcsEntity> entities, DebugDataContainer debugDataContainer) {
        for (EcsEntity entity : entities) {
            TransformComponent transform = entity.getComponent(TransformComponent.class);
            PhysicsComponent physics = entity.getComponent(PhysicsComponent.class);

            transform.position.y += gravity.y * delta * delta / 2;
            transform.position.add(physics.velocity.scl(delta));
            blockGround(transform, physics);
        }
    }

    private void blockGround(TransformComponent transform, PhysicsComponent physics) {
        int height = Gdx.graphics.getHeight();

        if (transform.position.y <= 0 || transform.position.y >= height) {
            physics.velocity.y = 0;
        }
        transform.position.y = MathUtils.clamp(transform.position.y, 0, height);
    }

}
