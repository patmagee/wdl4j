package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class StructType implements Type {

    private String name;

    private StructType(String name) {
        this.name = name;

    }

    @Override
    public String getTypeName() {
        return name;
    }

    private final static Set<StructType> INSTANCES = new HashSet<>();

    public static StructType getType(@NonNull String name) {
        for (StructType instance : INSTANCES) {
            if (instance.name.equals(name)) {
                return instance;
            }
        }

        StructType instance = new StructType(name);
        INSTANCES.add(instance);
        return instance;
    }
}
