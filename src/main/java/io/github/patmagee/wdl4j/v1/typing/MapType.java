package io.github.patmagee.wdl4j.v1.typing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MapType extends Type {

    @NonNull
    private final Type keyType;
    @NonNull
    private final Type valueType;

    private final List<String> literalNames;

    public static MapType getType(Type keyType, Type valueType) {
        Objects.requireNonNull(keyType, "keyType of a MapType cannot be null");
        Objects.requireNonNull(valueType, "valueType of a MapType cannot be null");
        return new MapType(keyType, valueType, null);
    }

    public static MapType getType(Type keyType, Type valueType, List<String> literalNames) {
        Objects.requireNonNull(keyType, "keyType of a MapType cannot be null");
        Objects.requireNonNull(valueType, "valueType of a MapType cannot be null");
        return new MapType(keyType, valueType, literalNames);
    }

    @Override
    public boolean isCoercibleTo(@NonNull Type toType) {
        if (toType instanceof MapType) {
            MapType mapToType = (MapType) toType;
            return keyType.isCoercibleTo(mapToType) && valueType.isCoercibleTo(valueType);
        } else if (toType instanceof StructType && literalNames != null && !literalNames.isEmpty()) {
            StructType structToType = (StructType) toType;
            Map<String, Type> members = structToType.getMembers();
            if (members == null || members.isEmpty()) {
                return false;
            } else if (!members.keySet().containsAll(literalNames)) {
                return false;
            }

            Set<String> toKeys = new HashSet<>(members.keySet());

            for (String k : literalNames) {
                if (!(toKeys.contains(k) && members.get(k).isCoercibleTo(valueType))) {
                    return false;
                }
            }

            toKeys.removeAll(literalNames);
            for (String optionalK : toKeys) {
                if (!(members.get(optionalK) instanceof OptionalType)) {
                    return false;
                }
            }
            return true;

        }

        return super.isCoercibleTo(toType);
    }

    @Override
    public String getTypeName() {
        return "Map[" + keyType.getTypeName() + "," + valueType.getTypeName() + "]";
    }

}
