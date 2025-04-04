package com.github.ashvard.gdx.simple.input.processor;

import com.github.ashvard.gdx.simple.input.processor.touch.TouchCancelled;
import com.github.ashvard.gdx.simple.input.processor.touch.TouchDown;
import com.github.ashvard.gdx.simple.input.processor.touch.TouchDragged;
import com.github.ashvard.gdx.simple.input.processor.touch.TouchUp;

public class InputTouch {

    public final TouchDown touchDown = new TouchDown();
    public final TouchUp touchUp = new TouchUp();
    public final TouchCancelled touchCancelled = new TouchCancelled();
    public final TouchDragged touchDragged = new TouchDragged();

    public InputTouch() {
        reset();
    }

    public void reset() {
        touchDown.reset();
        touchUp.reset();
        touchCancelled.reset();
        touchDragged.reset();
    }

}
