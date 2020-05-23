package io.github.patmagee.wdl4j.v1.stdlib;

import io.github.patmagee.wdl4j.v1.api.EngineFunction;
import io.github.patmagee.wdl4j.v1.exception.ArityException;
import io.github.patmagee.wdl4j.v1.exception.TypeCoercionException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.AnyType;
import io.github.patmagee.wdl4j.v1.typing.ArrayType;
import io.github.patmagee.wdl4j.v1.typing.PairType;
import io.github.patmagee.wdl4j.v1.typing.Type;

import java.util.List;

public class Zip implements EngineFunction {

    @Override
    public Type evaluateReturnType(List<Type> argumentTypes) throws WdlValidationError {
        Type firstArgument = argumentTypes.get(0);
        Type secondArgument = argumentTypes.get(1);
        Type requiredType = ArrayType.getType(AnyType.getType(), false);

        if (!firstArgument.isCoercibleTo(requiredType)) {
            throw new TypeCoercionException("Illegal argument type for zip function, expecting one Array[X] but got " + firstArgument
                    .getTypeName());
        }

        if (!secondArgument.isCoercibleTo(requiredType)) {
            throw new TypeCoercionException("Illegal argument type for zip function, expecting one Array[Y] but got " + secondArgument
                    .getTypeName());
        }

        Type firstInnerType = ((ArrayType) firstArgument).getInnerType();
        Type secondInnerType = ((ArrayType) secondArgument).getInnerType();
        return ArrayType.getType(PairType.getType(firstInnerType, secondInnerType), false);
    }

    @Override
    public void checkArity(List<Type> argumentTypes) throws ArityException {
        if (argumentTypes == null || argumentTypes.size() != 2) {
            throw new ArityException("Invalid number of arguments for function cross. Expecting 2 but got " + (
                    argumentTypes == null
                    ? "none"
                    : argumentTypes.size()));
        }
    }
}
