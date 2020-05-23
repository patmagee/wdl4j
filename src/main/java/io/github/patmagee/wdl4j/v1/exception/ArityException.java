package io.github.patmagee.wdl4j.v1.exception;

public class ArityException extends WdlValidationError {

    public ArityException() {
        super();
    }

    public ArityException(String s) {
        super(s);
    }

    public ArityException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
