package com.github.ashvard.gdx.simple.input.processor;

import com.github.ashvard.gdx.simple.input.ActionConst;

public class InputKey {

    public int keyDown;
    public int keyUp;
    public char keyTyped;

    public boolean isChanged = false;

    public void reset() {
        keyDown = ActionConst.EMPTY_ACTION;
        keyUp = ActionConst.EMPTY_ACTION;
        keyTyped = (char) ActionConst.EMPTY_ACTION;

        isChanged = false;
    }

}
