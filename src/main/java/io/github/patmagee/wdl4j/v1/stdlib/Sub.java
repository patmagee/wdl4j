package io.github.patmagee.wdl4j.v1.stdlib;

import io.github.patmagee.wdl4j.v1.api.EngineFunction;
import io.github.patmagee.wdl4j.v1.exception.ArityException;
import io.github.patmagee.wdl4j.v1.exception.TypeCoercionException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.StringType;
import io.github.patmagee.wdl4j.v1.typing.Type;

import java.util.List;

public class Sub implements EngineFunction {

    @Override
    public Type evaluateReturnType(List<Type> argumentTypes) throws WdlValidationError {
        Type argumentType = argumentTypes.get(0);
        Type regexType = argumentTypes.get(1);
        Type subType = argumentTypes.get(2);

        if (!argumentType.isCoercibleTo(StringType.getType())) {
            throw new TypeCoercionException(
                    "Illegal argument type for sub function argument [1], expecting String but got " + argumentType.getTypeName());
        }

        if (!(regexType instanceof StringType)) {

            throw new TypeCoercionException(
                    "Illegal argument type for sub function argument [2], expecting String but got " + argumentType.getTypeName());
        }

        if (!subType.isCoercibleTo(StringType.getType())) {
            throw new TypeCoercionException(
                    "Illegal argument type for sub function argument [3], expecting String but got " + argumentType.getTypeName());
        }

        return StringType.getType();
    }

    @Override
    public void checkArity(List<Type> argumentTypes) throws ArityException {
        if (argumentTypes == null || argumentTypes.size() != 3) {
            throw new ArityException("Invalid number of arguments for function sub. Expecting 3 but got " + (
                    argumentTypes == null
                    ? "none"
                    : argumentTypes.size()));
        }
    }
}
