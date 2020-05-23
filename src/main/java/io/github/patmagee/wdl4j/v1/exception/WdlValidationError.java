package io.github.patmagee.wdl4j.v1.exception;

public class WdlValidationError extends Exception {

    public WdlValidationError() {
        super();
    }

    public WdlValidationError(String s) {
        super(s);
    }

    public WdlValidationError(String s, Throwable throwable) {
        super(s, throwable);
    }
}
