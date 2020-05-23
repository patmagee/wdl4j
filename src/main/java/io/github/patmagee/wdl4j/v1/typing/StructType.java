package io.github.patmagee.wdl4j.v1.typing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Map;
import java.util.Objects;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StructType extends Type {

    @NonNull
    private final String name;

    private final Map<String, Type> members;

    public static StructType getType(String name) {
        Objects.requireNonNull(name, "The name of a struct type cannot be null");
        return new StructType(name, null);
    }

    public static StructType getType(String name, Map<String, Type> members) {
        Objects.requireNonNull(name, "The name of a struct type cannot be null");
        return new StructType(name, members);
    }

    @Override
    public boolean isCoercibleTo(@NonNull Type toType) {
        if (toType instanceof StructType) {
            StructType structToType = (StructType) toType;
            if (structToType.name.equals(name)) {
                return true;
            } else if (structToType.getMembers() != null && members != null) {
                return structToType.members.equals(members);
            } else {
                return false;
            }
        }
        return super.isCoercibleTo(toType);
    }

    @Override
    public String getTypeName() {
        return name;
    }
}
