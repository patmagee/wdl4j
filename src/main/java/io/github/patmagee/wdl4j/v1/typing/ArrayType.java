package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class ArrayType implements Type {

    private Type innerType;
    private Boolean nonEmpty;

    private ArrayType(Type innerType, boolean nonEmpty) {
        this.innerType = innerType;
        this.nonEmpty = nonEmpty;
    }

    @Override
    public String getTypeName() {
        return "Array[" + innerType.getTypeName() + "]" + (nonEmpty ? "+" : "");
    }

    private final static Set<ArrayType> INSTANCES = new HashSet<>();

    public static ArrayType getType(Type type, boolean nonEmpty) {
        return INSTANCES.stream()
                        .filter(a -> a.innerType.equals(type) && a.nonEmpty == nonEmpty)
                        .findFirst()
                        .orElseGet(() -> {
                            ArrayType typeInstance = new ArrayType(type, nonEmpty);
                            INSTANCES.add(typeInstance);
                            return typeInstance;
                        });

    }

}
