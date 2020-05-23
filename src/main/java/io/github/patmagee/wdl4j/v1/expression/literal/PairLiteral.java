package io.github.patmagee.wdl4j.v1.expression.literal;

import io.github.patmagee.wdl4j.v1.Namespace;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.expression.Expression;
import io.github.patmagee.wdl4j.v1.typing.PairType;
import io.github.patmagee.wdl4j.v1.typing.Type;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class PairLiteral extends Expression {

    Expression leftValue;
    Expression rightValue;

    public PairLiteral(@NonNull Expression leftValue, @NonNull Expression rightValue, @NonNull int id) {
        super(id);
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    @Override
    public Type typeCheck(WdlElement target, Namespace namespace) throws WdlValidationError {
        Type leftType = leftValue.typeCheck(target, namespace);
        Type rightType = rightValue.typeCheck(target, namespace);

        return PairType.getType(leftType, rightType);
    }
}

