package com.github.ashvard.gdx.happycrab.screen.level.level.physics;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by user on 13.01.2019.
 */
public interface ShapePosUpdater<S extends Shape2D> {

    void update(S shape, Vector2 vector2);

}
