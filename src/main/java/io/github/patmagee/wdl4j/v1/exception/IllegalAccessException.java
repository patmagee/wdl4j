package io.github.patmagee.wdl4j.v1.exception;

public class IllegalAccessException extends WdlValidationError {

    public IllegalAccessException() {
        super();
    }

    public IllegalAccessException(String s) {
        super(s);
    }

    public IllegalAccessException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
