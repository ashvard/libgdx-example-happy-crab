package com.github.ashvard.gdx.simple.console.exception;

import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by user on 31.12.2018.
 */
public class ConsoleException extends GdxRuntimeException {

    public ConsoleException(String message) {
        super(message);
    }

    public ConsoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsoleException(Throwable cause) {
        super(cause);
    }

}
