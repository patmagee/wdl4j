package io.github.patmagee.wdl4j.v1.typing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StructType extends Type {

    private final static Set<StructType> INSTANCES = new HashSet<>();
    @NonNull
    private final String name;

    public static StructType getType(String name) {
        Objects.requireNonNull(name, "The name of a struct type cannot be null");

        for (StructType instance : INSTANCES) {
            if (instance.name.equals(name)) {
                return instance;
            }
        }

        StructType instance = new StructType(name);
        INSTANCES.add(instance);
        return instance;
    }

    @Override
    public String getTypeName() {
        return name;
    }
}
