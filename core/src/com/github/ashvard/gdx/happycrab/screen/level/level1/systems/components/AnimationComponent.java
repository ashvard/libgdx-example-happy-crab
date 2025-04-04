package com.github.ashvard.gdx.happycrab.screen.level.level1.systems.components;

import com.artemis.Component;
import com.github.ashvard.gdx.simple.animation.component.SimpleAnimationComponent;

public class AnimationComponent extends Component {

    public SimpleAnimationComponent simpleAnimationComponent;

    public AnimationComponent(SimpleAnimationComponent simpleAnimationComponent) {
        this.simpleAnimationComponent = simpleAnimationComponent;
    }

    public AnimationComponent(){}

}
