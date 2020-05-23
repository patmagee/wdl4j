package io.github.patmagee.wdl4j.v1.expression.literal;

import io.github.patmagee.wdl4j.v1.Namespace;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.TypeCoercionException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.expression.Expression;
import io.github.patmagee.wdl4j.v1.typing.AnyType;
import io.github.patmagee.wdl4j.v1.typing.ArrayType;
import io.github.patmagee.wdl4j.v1.typing.Type;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class ArrayLiteral extends Expression {

    List<Expression> values;

    public ArrayLiteral(@NonNull List<Expression> values, @NonNull int id) {
        super(id);
        this.values = values;
    }

    @Override
    public Type typeCheck(WdlElement target, Namespace namespace) throws WdlValidationError {
        if (values == null || values.isEmpty()) {
            return ArrayType.getType(AnyType.getType(), false);
        } else {
            Type targetType = null;
            for (Expression expression : values) {
                Type evalutedType = expression.typeCheck(target, namespace);

                if (targetType == null) {
                    targetType = evalutedType;
                } else if (!evalutedType.isCoercibleTo(targetType)) {
                    throw new TypeCoercionException("Illegal type coercion. Cannot coerce type " + evalutedType.getTypeName() + " to " + targetType
                            .getTypeName());
                }
            }
            return ArrayType.getType(targetType, false);
        }
    }
}
