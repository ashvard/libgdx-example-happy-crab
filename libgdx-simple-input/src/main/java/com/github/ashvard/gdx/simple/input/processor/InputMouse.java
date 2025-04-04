package com.github.ashvard.gdx.simple.input.processor;

import com.github.ashvard.gdx.simple.input.processor.mouse.MouseMoved;
import com.github.ashvard.gdx.simple.input.processor.mouse.Scrolled;

public class InputMouse {

    public final MouseMoved mouseMoved = new MouseMoved();
    public final Scrolled scrolled = new Scrolled();

    public boolean isChanged = false;

    public InputMouse() {
        reset();
    }

    public void reset() {
        mouseMoved.reset();
        scrolled.reset();

        isChanged = false;
    }

}
