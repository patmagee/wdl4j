package io.github.patmagee.wdl4j.v1.expression;

import io.github.patmagee.wdl4j.v1.Namespace;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.ExpressionEvaluationException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.FloatType;
import io.github.patmagee.wdl4j.v1.typing.IntType;
import io.github.patmagee.wdl4j.v1.typing.Type;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Signed extends Expression {

    Expression expression;
    Operation operation;

    public enum Operation {
        PLUS, MINUS
    }

    public Signed(@NonNull Expression expression, @NonNull Operation operation, @NonNull int id) {
        super(id);
        this.expression = expression;
        this.operation = operation;
    }

    @Override
    public Type typeCheck(WdlElement target, Namespace namespace) throws WdlValidationError {
        Type evaluatedType = expression.typeCheck(target, namespace);

        if (evaluatedType instanceof FloatType || evaluatedType instanceof IntType) {
            return evaluatedType;
        } else {
            throw new ExpressionEvaluationException(
                    "Illegal Type for signed operation. Expecting an expression evaluating to one of [Int,Float] but got " + evaluatedType
                            .getTypeName());
        }
    }
}
