package io.github.patmagee.wdl4j.v1.api;

import io.github.patmagee.wdl4j.v1.exception.ArityException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.Type;

import java.util.List;

public interface EngineFunction {

    Type evaluateReturnType(List<Type> argumentTypes) throws WdlValidationError;

    void checkArity(List<Type> argumentTypes) throws ArityException;
}
