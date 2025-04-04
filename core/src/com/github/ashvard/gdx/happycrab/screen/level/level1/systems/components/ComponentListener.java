package com.github.ashvard.screen.level.level1.systems.components;

import com.artemis.Component;

public interface ComponentListener<T extends Component> {

    void update(T component);

}
