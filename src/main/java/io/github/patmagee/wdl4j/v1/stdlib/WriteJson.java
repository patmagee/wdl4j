package io.github.patmagee.wdl4j.v1.stdlib;

import io.github.patmagee.wdl4j.v1.api.EngineFunction;
import io.github.patmagee.wdl4j.v1.exception.ArityException;
import io.github.patmagee.wdl4j.v1.exception.TypeCoercionException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.ArrayType;
import io.github.patmagee.wdl4j.v1.typing.FileType;
import io.github.patmagee.wdl4j.v1.typing.ObjectType;
import io.github.patmagee.wdl4j.v1.typing.Type;

import java.util.List;

public class WriteJson implements EngineFunction {

    @Override
    public Type evaluateReturnType(List<Type> argumentTypes) throws WdlValidationError {
        return FileType.getType();
    }

    @Override
    public void checkArity(List<Type> argumentTypes) throws ArityException {
        if (argumentTypes == null || argumentTypes.size() != 1){
            throw new ArityException("Invalid number of arguments for function write_json. Expecting 1 but got " + (argumentTypes == null ? "none" : argumentTypes.size()));
        }
    }
}
