package io.github.patmagee.wdl4j.v1.stdlib;

import io.github.patmagee.wdl4j.v1.api.EngineFunction;
import io.github.patmagee.wdl4j.v1.exception.ArityException;
import io.github.patmagee.wdl4j.v1.typing.FileType;
import io.github.patmagee.wdl4j.v1.typing.Type;

import java.util.List;

public class Stdout implements EngineFunction {

    @Override
    public Type evaluateReturnType(List<Type> argumentTypes) {
        return FileType.getType();
    }

    @Override
    public void checkArity(List<Type> argumentTypes) throws ArityException {
        if (argumentTypes != null && argumentTypes.size() > 0){
            throw new ArityException("Invalid number of arguments for function stdout. Expecting none but got " + argumentTypes.size());
        }
    }
}
