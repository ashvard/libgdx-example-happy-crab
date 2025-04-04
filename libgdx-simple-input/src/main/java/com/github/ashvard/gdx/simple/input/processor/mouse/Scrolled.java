package com.github.ashvard.gdx.simple.input.processor.mouse;

import com.github.ashvard.gdx.simple.input.ActionConst;

public class Scrolled {

    public float amountX;
    public float amountY;

    public boolean isChanged = false;

    public Scrolled() {
        reset();
    }

    public void reset() {
        amountX = ActionConst.EMPTY_DATA;
        amountY = ActionConst.EMPTY_DATA;

        isChanged = false;
    }
}
