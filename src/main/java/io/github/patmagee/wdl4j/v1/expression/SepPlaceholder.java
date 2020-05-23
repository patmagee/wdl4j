package io.github.patmagee.wdl4j.v1.expression;

import io.github.patmagee.wdl4j.v1.Namespace;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.TypeCoercionException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.StringType;
import io.github.patmagee.wdl4j.v1.typing.Type;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class SepPlaceholder extends Expression {

    Expression value;

    public SepPlaceholder(@NonNull Expression value, @NonNull int id) {
        super(id);
        this.value = value;
    }

    @Override
    public Type typeCheck(WdlElement target, Namespace namespace) throws WdlValidationError {
        Type valueType = value.typeCheck(target, namespace);
        if (!valueType.isCoercibleTo(StringType.getType())) {
            throw new TypeCoercionException(
                    "Illegal type evaluation for sep placeholder. Expression must be coercible to type String but got " + valueType
                            .getTypeName());
        }
        return StringType.getType();
    }
}
