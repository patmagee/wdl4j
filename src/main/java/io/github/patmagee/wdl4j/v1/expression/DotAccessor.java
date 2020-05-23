package io.github.patmagee.wdl4j.v1.expression;

import io.github.patmagee.wdl4j.v1.Namespace;
import io.github.patmagee.wdl4j.v1.Struct;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.IllegalAccessException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.*;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
@EqualsAndHashCode(callSuper = true)
public class DotAccessor extends Expression {

    Expression element;
    String name;

    public DotAccessor(@NonNull Expression element, @NonNull String name, @NonNull int id) {
        super(id);
        this.element = element;
        this.name = name;
    }

    @Override
    public Type typeCheck(WdlElement target, Namespace namespace) throws WdlValidationError {
        Type evaluatedType = element.typeCheck(target, namespace);

        if (evaluatedType instanceof MapType) {
            MapType evaluatedMapType = (MapType) evaluatedType;
            if ((evaluatedMapType.getKeyType() instanceof StringType)) {
                throw new IllegalAccessException(
                        "Trying to access Map value with dot notation, however Key Type is not String");
            }
            return evaluatedMapType.getValueType();

        } else if (evaluatedType instanceof PairType) {

            PairType evaluatedPairType = (PairType) evaluatedType;
            if (name.equals("left")) {
                return evaluatedPairType.getLeftType();
            } else if (name.equals("right")) {
                return evaluatedPairType.getRightType();
            } else {
                throw new IllegalAccessException("Only 'left' and 'right' accessors can be used with a pair type");
            }

        } else if (evaluatedType instanceof StructType) {
            StructType evaluatedStructType = (StructType) evaluatedType;

            if (evaluatedStructType.getMembers() == null) {
                Struct struct = namespace.getStruct(evaluatedStructType.getName());
                return struct.getMembers()
                             .stream()
                             .filter(decl -> decl.getName().equals(name))
                             .findFirst()
                             .orElseThrow(() -> new IllegalAccessException("Attempting to access attribute " + name + " from struct " + struct
                                     .getName() + ", however no such attribute is defined"))
                             .getDeclType();
            } else {
                return Optional.ofNullable(evaluatedStructType.getMembers().get(name))
                               .orElseThrow(() -> new IllegalAccessException("Attempting to access attribute " + name + " from struct " + evaluatedStructType
                                       .getName() + ", however no such attribute is defined"));
            }

        } else if (evaluatedType instanceof ObjectType) {
            ObjectType evaluatedObjectType = (ObjectType) evaluatedType;
            if (evaluatedObjectType.getMembers() != null && !evaluatedObjectType.getMembers().isEmpty()) {
                Type objectElementType = evaluatedObjectType.getMembers().get(name);
                if (objectElementType == null) {
                    throw new IllegalAccessException("Attempting to acess attribute " + name + " from object, however no such attribute is defined");
                } else {
                    return objectElementType;
                }
            } else {
                return AnyType.getType();
            }
        } else {
            throw new IllegalAccessException("Dot notation on element of type: " + evaluatedType.getTypeName() + " is not supported");
        }
    }
}
