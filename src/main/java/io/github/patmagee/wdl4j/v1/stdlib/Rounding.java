package io.github.patmagee.wdl4j.v1.stdlib;

import io.github.patmagee.wdl4j.v1.api.EngineFunction;
import io.github.patmagee.wdl4j.v1.exception.ArityException;
import io.github.patmagee.wdl4j.v1.exception.TypeCoercionException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.FloatType;
import io.github.patmagee.wdl4j.v1.typing.Type;

import java.util.List;

public class Rounding implements EngineFunction {

    public enum Direction {
        UP, DOWN, BOTH
    }

    private final Direction direction;
    private final String funcName;

    public Rounding(String name, Direction direction) {
        this.direction = direction;
        this.funcName = name;
    }

    @Override
    public Type evaluateReturnType(List<Type> argumentTypes) throws WdlValidationError {
        Type argumentType = argumentTypes.get(0);
        if (!argumentType.isCoercibleTo(FloatType.getType())) {
            throw new TypeCoercionException("Illegal argument type for basename function " + funcName + ", expecting Float but got " + argumentType
                    .getTypeName());
        }
        return FloatType.getType();
    }

    @Override
    public void checkArity(List<Type> argumentTypes) throws ArityException {
        if (argumentTypes == null || argumentTypes.size() != 1) {
            throw new ArityException("Invalid number of arguments for function " + funcName + " . Expecting 1 but got " + (
                    argumentTypes == null
                    ? "none"
                    : argumentTypes.size()));
        }
    }
}
