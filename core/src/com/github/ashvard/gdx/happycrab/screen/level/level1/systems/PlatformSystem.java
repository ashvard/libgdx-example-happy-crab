package com.github.ashvard.gdx.happycrab.screen.level.level1.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.components.PlatformComponent;
import com.github.ashvard.gdx.simple.structure.screen.EqualsUtils;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;

@All(PlatformComponent.class)
public class PlatformSystem extends IteratingSystem {

    protected ComponentMapper<PlatformComponent> platformMapper;
    protected ComponentMapper<PhysicsBodyComponent> physicsMapper;

    @Override
    protected void process(int entityId) {
        PlatformComponent platformComponent = platformMapper.get(entityId);
        PhysicsBodyComponent physicsBodyComponent = physicsMapper.get(entityId);
        if (!platformComponent.isInit()) {
            platformComponent.init();
            physicsBodyComponent.body.setLinearVelocity(platformComponent.velocity);
        }

        Transform transform = physicsBodyComponent.body.getTransform();
        Vector2 position = transform.getPosition();
        if (
                EqualsUtils.isEquals(position.x, platformComponent.endX, 0.005f) ||
                        EqualsUtils.isEquals(position.y, platformComponent.endY, 0.005f)
        ) {
            if (platformComponent.isPingPong) {
                physicsBodyComponent.body.setLinearVelocity(platformComponent.getReverseVector());
            } else {
                physicsBodyComponent.body.setLinearVelocity(Vector2.Zero);
            }
        }

        if (
                EqualsUtils.isEquals(position.x, platformComponent.startX, 0.005f) ||
                        EqualsUtils.isEquals(position.y, platformComponent.startY, 0.005f)
        ) {
            if (platformComponent.isPingPong) {
                physicsBodyComponent.body.setLinearVelocity(platformComponent.velocity);
            }
        }

    }

}
