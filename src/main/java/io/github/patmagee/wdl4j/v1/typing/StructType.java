package io.github.patmagee.wdl4j.v1.typing;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class StructType extends Type {

    private String name;

    private StructType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getTypeName() {
        return name;
    }

    private final static Set<StructType> INSTANCES = new HashSet<>();

    public static StructType getType(String name) {
        Objects.requireNonNull(name,"The name of a struct type cannot be null");

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
