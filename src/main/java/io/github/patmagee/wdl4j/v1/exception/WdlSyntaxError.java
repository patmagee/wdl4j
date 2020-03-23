package io.github.patmagee.wdl4j.v1.exception;

import io.github.patmagee.wdl4j.v1.errors.SyntaxError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WdlSyntaxError extends RuntimeException {

    List<SyntaxError> errors;

    public WdlSyntaxError(String s, Throwable throwable) {
        super(s, throwable);
        errors = Collections.emptyList();
    }

    public WdlSyntaxError(SyntaxError syntaxError) {
        super();
        this.errors = Arrays.asList(syntaxError);
    }

    public WdlSyntaxError(List<SyntaxError> syntaxErrors) {
        super();
        this.errors = new ArrayList<>(syntaxErrors);
    }

    public List<SyntaxError> getErrors() {
        return errors;
    }

    public String getMessage() {
        if (errors != null && errors.size() > 0) {
            return getErrorString();
        } else {
            return super.getMessage();
        }
    }

    private String getErrorString() {
        return "\n" + String.join("\n", errors.stream().map(SyntaxError::toString).collect(Collectors.toList()));
    }

}
