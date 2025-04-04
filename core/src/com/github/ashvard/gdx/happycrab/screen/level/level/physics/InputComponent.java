package com.github.ashvard.screen.level.level.physics;

import com.github.ashvard.gdx.ecs.simple.engine.EcsComponent;
import com.github.ashvard.gdx.simple.input.InputActions;

public class InputComponent implements EcsComponent {
    public InputActions inputActions;
    public InputComponent(InputActions inputActions) {
        this.inputActions = inputActions;
    }
}
