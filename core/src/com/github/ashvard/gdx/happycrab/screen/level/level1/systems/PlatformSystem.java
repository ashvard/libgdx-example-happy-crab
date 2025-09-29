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
        // достигнута конечная точка по Х
        if (
                (platformComponent.endX - platformComponent.startX > 0 &&
                        position.x - platformComponent.endX > 0f
                ) || (platformComponent.endX - platformComponent.startX < 0 &&
                        position.x - platformComponent.endX < 0f
                )
        ) {
            if (platformComponent.isPingPong) {
                physicsBodyComponent.body.setLinearVelocity(platformComponent.getReverseVector());
            } else {
                physicsBodyComponent.body.setLinearVelocity(Vector2.Zero);
            }
        } else if (
            // достигнута конечная точка по Y
                (platformComponent.endY - platformComponent.startY > 0 &&
                        position.y - platformComponent.endY > 0f
                ) || (platformComponent.endY - platformComponent.startY < 0 &&
                        position.y - platformComponent.endY < 0f
                )
        ) {
            if (platformComponent.isPingPong) {
                physicsBodyComponent.body.setLinearVelocity(platformComponent.getReverseVector());
            } else {
                physicsBodyComponent.body.setLinearVelocity(Vector2.Zero);
            }
        }

        // достигнута начальная точка по Х
        if (
                (platformComponent.endX - platformComponent.startX > 0 &&
                        position.x - platformComponent.startX < 0f
                ) || (platformComponent.endX - platformComponent.startX < 0 &&
                        position.x - platformComponent.startX > 0f
                )
        ) {
            if (platformComponent.isPingPong) {
                physicsBodyComponent.body.setLinearVelocity(platformComponent.velocity);
            }
        } else if (
            // достигнута начальная точка по y
                (platformComponent.endY - platformComponent.startY > 0 &&
                        position.y - platformComponent.startY < 0f
                ) || (platformComponent.endY - platformComponent.startY < 0 &&
                        position.y - platformComponent.startY > 0f
                )
        ) {
            if (platformComponent.isPingPong) {
                physicsBodyComponent.body.setLinearVelocity(platformComponent.velocity);
            }
        }



    }

}
