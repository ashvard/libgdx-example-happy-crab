package com.github.ashvard.gdx.simple.input.processor.touch;

import com.github.ashvard.gdx.simple.input.ActionConst;

public class TouchDown {

    public int screenX;
    public int screenY;
    public int pointer;
    public int button;

    public boolean isChanged = false;

    public TouchDown() {
        reset();
    }

    public void reset() {
        screenX = ActionConst.EMPTY_DATA;
        screenY = ActionConst.EMPTY_DATA;
        pointer = ActionConst.EMPTY_DATA;
        button = ActionConst.EMPTY_DATA;

        isChanged = false;
    }

}
