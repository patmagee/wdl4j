package io.github.patmagee.wdl4j.v1.expression;

import io.github.patmagee.wdl4j.v1.Namespace;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.ExpressionEvaluationException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.BooleanType;
import io.github.patmagee.wdl4j.v1.typing.Type;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class IfThenElse extends Expression {

    Expression condition;
    Expression ifTrue;
    Expression ifFalse;

    public IfThenElse(@NonNull Expression condition, @NonNull Expression ifTrue, Expression ifFalse, @NonNull int id) {
        super(id);
        this.condition = condition;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    @Override
    public Type typeCheck(WdlElement target, Namespace namespace) throws WdlValidationError {
        Type conditionType = condition.typeCheck(target, namespace);

        if (!(conditionType instanceof BooleanType) || conditionType.isCoercibleTo(BooleanType.getType())) {
            throw new ExpressionEvaluationException(
                    "Illegal condition in if then else. Expecting a condition coercible to type Boolean but got " + conditionType
                            .getTypeName());
        }

        Type ifTrueType = ifTrue.typeCheck(target, namespace);
        Type ifFalseType = ifFalse.typeCheck(target, namespace);

        if (!ifFalseType.isCoercibleTo(ifTrueType)) {
            throw new ExpressionEvaluationException("Illegal return type for else statement. Expecting a type of " + ifTrueType
                    .getTypeName() + " but got " + ifFalseType.getTypeName());
        }
        return ifTrueType;
    }
}
