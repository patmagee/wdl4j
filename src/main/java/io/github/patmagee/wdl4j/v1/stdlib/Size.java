package io.github.patmagee.wdl4j.v1.stdlib;

import io.github.patmagee.wdl4j.v1.api.EngineFunction;
import io.github.patmagee.wdl4j.v1.exception.ArityException;
import io.github.patmagee.wdl4j.v1.exception.TypeCoercionException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Size implements EngineFunction {

    private final List<Type> acceptableTypes = Arrays.asList(FileType.getType(),
                                                             OptionalType.getType(FileType.getType()),
                                                             ArrayType.getType(FileType.getType(), false),
                                                             ArrayType.getType(OptionalType.getType(FileType.getType()),
                                                                               false));

    @Override
    public Type evaluateReturnType(List<Type> argumentTypes) throws WdlValidationError {
        Type argumentType = argumentTypes.get(0);
        if (argumentTypes.size() == 2) {
            Type unitType = argumentTypes.get(1);
            if (!unitType.isCoercibleTo(StringType.getType())) {
                throw new TypeCoercionException(
                        "Illegal argument type for optional 'Units' parameter of size function. Expecting string but got " + unitType
                                .getTypeName());
            }
        }

        boolean canCoerce = acceptableTypes.stream().anyMatch(argumentType::isCoercibleTo);
        String typeNames = acceptableTypes.stream().map(Type::getTypeName).collect(Collectors.joining(","));

        if (!canCoerce) {
            throw new TypeCoercionException("Illegal argument type for write_tsv function, expecting one of [" + typeNames + "] but got " + argumentType
                    .getTypeName());
        }
        return FileType.getType();
    }

    @Override
    public void checkArity(List<Type> argumentTypes) throws ArityException {
        if (argumentTypes == null || argumentTypes.size() < 1 || argumentTypes.size() > 2) {
            throw new ArityException("Invalid number of arguments for function write_tsv. Expecting [1,2] but got " + (
                    argumentTypes == null
                    ? "none"
                    : argumentTypes.size()));
        }
    }
}
