package io.github.patmagee.wdl4j.v1.stdlib;

import io.github.patmagee.wdl4j.v1.api.EngineFunction;
import io.github.patmagee.wdl4j.v1.exception.ArityException;
import io.github.patmagee.wdl4j.v1.exception.TypeCoercionException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.AnyType;
import io.github.patmagee.wdl4j.v1.typing.BooleanType;
import io.github.patmagee.wdl4j.v1.typing.OptionalType;
import io.github.patmagee.wdl4j.v1.typing.Type;

import java.util.List;

public class Defined implements EngineFunction {

    @Override
    public Type evaluateReturnType(List<Type> argumentTypes) throws WdlValidationError {
        Type argumentType = argumentTypes.get(0);
        Type requiredType = OptionalType.getType(AnyType.getType());

        if (!argumentType.isCoercibleTo(requiredType)) {
            throw new TypeCoercionException("Illegal argument type for defined function, expecting X? but got " + argumentType
                    .getTypeName());
        }

        return BooleanType.getType();
    }

    @Override
    public void checkArity(List<Type> argumentTypes) throws ArityException {
        if (argumentTypes == null || argumentTypes.size() != 1) {
            throw new ArityException("Invalid number of arguments for function defined. Expecting 1 but got " + (
                    argumentTypes == null
                    ? "none"
                    : argumentTypes.size()));
        }
    }
}
