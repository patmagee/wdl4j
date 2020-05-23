package io.github.patmagee.wdl4j.v1.expression.literal;

import io.github.patmagee.wdl4j.v1.Namespace;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.ExpressionEvaluationException;
import io.github.patmagee.wdl4j.v1.exception.TypeCoercionException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.expression.Expression;
import io.github.patmagee.wdl4j.v1.typing.AnyType;
import io.github.patmagee.wdl4j.v1.typing.MapType;
import io.github.patmagee.wdl4j.v1.typing.Type;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class MapLiteral extends Expression {

    @NonNull List<MapEntry> values;



    @Override
    public Type typeCheck(WdlElement target, Namespace namespace) throws WdlValidationError {
        if (values == null || values.isEmpty()) {
            return MapType.getType(AnyType.getType(), AnyType.getType());
        }

        Type targetKeyType = null;
        Type targetValueType = null;

        List<String> literalNames = new ArrayList<>();
        for (MapEntry entry : values) {
            Expression key = entry.getKey();
            Expression value = entry.getValue();
            if (entry.getKey() instanceof StringLiteral && !((StringLiteral) key).requiresEvaluation()) {
                literalNames.add(((StringLiteral) key).getUnevaluatedString());
            }

            Type expressionKeyType = key.typeCheck(target, namespace);
            Type expressionValueType = value.typeCheck(target, namespace);

            if (targetKeyType == null && targetValueType == null) {
                targetKeyType = expressionKeyType;
                targetValueType = expressionValueType;
            } else if (!expressionKeyType.isCoercibleTo(targetKeyType)) {
                throw new TypeCoercionException("Illegal type coercion. Cannot coerce type " + expressionKeyType.getTypeName() + " to " + targetKeyType
                        .getTypeName());

            } else if (!targetValueType.isCoercibleTo(expressionValueType)) {
                throw new TypeCoercionException("Illegal type coercion. Cannot coerce type " + expressionValueType.getTypeName() + " to " + targetValueType
                        .getTypeName());

            }

        }
        if (literalNames.size() == values.size()){
            return MapType.getType(targetKeyType,targetValueType,literalNames);
        } else {
            return MapType.getType(targetKeyType, targetValueType);
        }
    }

    public static class MapEntry {

        final Expression key;
        final Expression value;

        public MapEntry(Expression key, Expression value) {
            this.key = key;
            this.value = value;
        }

        public Expression getKey() {
            return key;
        }

        public Expression getValue() {
            return value;
        }
    }

    public MapLiteral(@NonNull List<MapEntry> values, @NonNull int id) {
        super(id);
        this.values = values;
    }
}
