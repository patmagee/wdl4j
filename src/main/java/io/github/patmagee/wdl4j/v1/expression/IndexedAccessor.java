package io.github.patmagee.wdl4j.v1.expression;

import io.github.patmagee.wdl4j.v1.Namespace;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.ExpressionEvaluationException;
import io.github.patmagee.wdl4j.v1.exception.IllegalAccessException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.ArrayType;
import io.github.patmagee.wdl4j.v1.typing.IntType;
import io.github.patmagee.wdl4j.v1.typing.MapType;
import io.github.patmagee.wdl4j.v1.typing.Type;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class IndexedAccessor extends Expression {

    Expression element;
    Expression expression;

    public IndexedAccessor(@NonNull Expression element, @NonNull Expression expression, @NonNull int id) {
        super(id);
        this.element = element;
        this.expression = expression;
    }

    @Override
    public Type typeCheck(WdlElement target, Namespace namespace) throws WdlValidationError {
        Type elementType = element.typeCheck(target, namespace);
        Type accessorType = expression.typeCheck(target, namespace);

        if (elementType instanceof MapType) {
            MapType mapType = (MapType) elementType;
            if (!accessorType.isCoercibleTo(mapType.getKeyType())) {
                throw new IllegalAccessException(
                        "Invalid key type for accessing map attribute. Expecting type coercible to " + mapType.getKeyType()
                                                                                                              .getTypeName() + " but got " + accessorType
                                .getTypeName());
            } else {
                return mapType.getValueType();
            }
        } else if (elementType instanceof ArrayType) {
            ArrayType arrayType = (ArrayType) elementType;
            if (!(accessorType instanceof IntType)) {
                throw new IllegalAccessException(
                        "Invalid index lookup for accessing Array element. Expecting type coercible to " + IntType.getType()
                                                                                                                  .getTypeName() + " but got " + accessorType
                                .getTypeName());
            }
            return arrayType.getInnerType();

        } else {
            throw new ExpressionEvaluationException(
                    "Error evaluating Expression. Attempting to index non indexable element " + elementType.getTypeName());
        }
    }
}