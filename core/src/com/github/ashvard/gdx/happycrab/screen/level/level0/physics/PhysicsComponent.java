package com.github.ashvard.gdx.happycrab.screen.level.level0.physics;


import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.github.ashvard.gdx.ecs.simple.engine.EcsComponent;

public class PhysicsComponent implements EcsComponent {

    public Vector2 velocity;        // скорость
    public Shape2D shape;           // физическая форма

}
