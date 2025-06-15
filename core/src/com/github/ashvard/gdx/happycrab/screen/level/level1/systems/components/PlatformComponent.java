package com.github.ashvard.gdx.happycrab.screen.level.level1.systems.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;

public class PlatformComponent extends PooledComponent {

    public float startX, startY;
    public float endX, endY;
    public float timeMs;
    public boolean isPingPong;

    public Vector2 velocity;
    public Vector2 reverseVelocity;

    public void init() {
        velocity = new Vector2(
                (endX - startX) / timeMs,
                (endY - startY) / timeMs
        );

        reverseVelocity = new Vector2(
                (endX == startX)? 0f : -velocity.x,
                (endY == startY)? 0f : -velocity.y
        );
    }

    public boolean isInit() {
        return velocity != null;
    }

    public Vector2 getReverseVector() {
        return reverseVelocity;
    }

    @Override
    protected void reset() {
        startX = 0;
        startY = 0;
        endX = 0;
        endY = 0;
        timeMs = 0;
        isPingPong = false;
        velocity = null;
    }


}
