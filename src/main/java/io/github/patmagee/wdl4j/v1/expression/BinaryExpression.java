package io.github.patmagee.wdl4j.v1.expression;

import io.github.patmagee.wdl4j.v1.Namespace;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.TypeCoercionException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.*;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class BinaryExpression extends Expression {

    Expression leftHandSide;
    Expression rightHandSide;
    BinaryOperation operation;

    public BinaryExpression(@NonNull Expression leftHandSide, @NonNull Expression rightHandSide, @NonNull BinaryOperation operation, @NonNull int id) {
        super(id);
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
        this.operation = operation;
    }

    public enum BinaryOperation {
        ADD,

        DIVIDE,

        EQUAL_TO,

        GREATER_THAN,

        GREATER_THAN_OR_EQUAL,

        LESS_THAN,

        LESS_THAN_OR_EQUAL,

        LOGICAL_AND,

        LOGICAL_OR,

        MOD,

        MULTIPLY,

        NOT_EQUAL_TO,

        SUBTRACT
    }

    @Override
    public Type typeCheck(WdlElement target, Namespace namespace) throws WdlValidationError {
        Type leftHandType = leftHandSide.typeCheck(target, namespace);
        Type rightHandType = rightHandSide.typeCheck(target, namespace);

        if (operation.equals(BinaryOperation.DIVIDE) || operation.equals(BinaryOperation.MULTIPLY) || operation.equals(
                BinaryOperation.SUBTRACT) || operation.equals(BinaryOperation.MOD)) {
            return typeCheckNumeric(leftHandType, rightHandType);
        } else if (operation.equals(BinaryOperation.LOGICAL_AND) || operation.equals(BinaryOperation.LOGICAL_OR)) {
            return typeCheckBoolean(leftHandType, rightHandType);
        } else if (operation.equals(BinaryOperation.NOT_EQUAL_TO) || operation.equals(BinaryOperation.EQUAL_TO)) {
            return BooleanType.getType();
        } else if (operation.equals(BinaryOperation.ADD) && rightHandType instanceof StringType) {
            if (leftHandType.isCoercibleTo(StringType.getType())) {
                return StringType.getType();
            } else {
                throw new TypeCoercionException("Illegal Type Coercion. Left hand side of " + operation.name() + " cannot be cast to a string type");
            }
        } else {
            return typeCheckhNumericOrString(leftHandType, rightHandType);
        }

    }

    private Type typeCheckBoolean(Type lhs, Type rhs) throws TypeCoercionException {
        Type booleanType = BooleanType.getType();
        if (!lhs.isCoercibleTo(booleanType)) {
            throw new TypeCoercionException("Illegal Type coercion. Left hand side of binary operation " + operation.name() + "cannot be cast to a boolean type");
        }

        if (!rhs.isCoercibleTo(booleanType)) {
            throw new TypeCoercionException("Illegal Type coercion. Left hand side of binary operation " + operation.name() + "cannot be cast to a boolean type");
        }

        return booleanType;

    }

    private Type typeCheckNumeric(Type lhs, Type rhs) throws TypeCoercionException {
        Type floatType = FloatType.getType();
        Type intType = IntType.getType();

        if (!lhs.isCoercibleTo(floatType) && !lhs.isCoercibleTo(intType)) {
            throw new TypeCoercionException("Illegal Type coercion. Left hand side of binary operation " + operation.name() + " cannot be cast to a numeric type");
        }

        if ((!rhs.isCoercibleTo(floatType) && !rhs.isCoercibleTo(intType))) {
            throw new TypeCoercionException("Illegal Type coercion. Right hand side of binary operation " + operation.name() + " cannot be cast to a numeric type");
        }

        if (lhs.equals(intType) || lhs.equals(floatType)) {
            return lhs;
        } else if (lhs.isCoercibleTo(intType)) {
            return intType;
        } else {
            return floatType;
        }
    }

    private Type typeCheckhNumericOrString(Type lhs, Type rhs) throws TypeCoercionException {

        Type targetType;
        if (lhs.equals(StringType.getType()) || lhs.equals(FloatType.getType()) || lhs.equals(IntType.getType())) {
            targetType = lhs;
        } else if (lhs.isCoercibleTo(FloatType.getType())) {
            targetType = FloatType.getType();
        } else if (lhs.isCoercibleTo(IntType.getType())) {
            targetType = IntType.getType();
        } else if (lhs.isCoercibleTo(StringType.getType())) {
            targetType = StringType.getType();
        } else {
            throw new TypeCoercionException("Illegal Type Coercion. Left hand side of binary opertion " + operation.name() + " cannot be cast to Float, Int or String type");
        }

        if (!targetType.isCoercibleTo(rhs)) {
            throw new TypeCoercionException("Illegal Type Coercion. Right hand side of binary opertion " + operation.name() + " cannot be cast to " + targetType
                    .getTypeName());
        }

        return targetType;

    }
}
