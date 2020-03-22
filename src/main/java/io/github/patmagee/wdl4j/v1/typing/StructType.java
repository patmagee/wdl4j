package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class StructType implements Type {

    private String name;

    private StructType(String name) {
        this.name = name;

    }

    private final static Set<StructType> INSTANCES = new HashSet<>();

    public static StructType getType(String name) {
        return INSTANCES.stream().filter(instance -> instance.name.equals(name)).findFirst().orElseGet(() -> {
            StructType structType = new StructType(name);
            INSTANCES.add(structType);
            return structType;
        });
    }

    @Override
    public String getTypeName() {
        return name;
    }
}
