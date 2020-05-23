package io.github.patmagee.wdl4j.v1.api;

import io.github.patmagee.wdl4j.v1.exception.NoSuchReferenceException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.Type;

import java.util.List;

public interface StandardLib {

    Type evaluateReturnType(String name, List<Type> arguments) throws WdlValidationError;

    EngineFunction getFunction(String name) throws NoSuchReferenceException;

}
