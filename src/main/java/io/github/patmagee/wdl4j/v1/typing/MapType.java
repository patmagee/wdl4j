package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class MapType implements Type {

    private Type keyType;
    private Type valueType;

    private MapType(Type keyType, Type valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }

    private final static Set<MapType> INSTANCES = new HashSet<>();

    public static MapType getType(Type key, Type value) {
        return INSTANCES.stream()
                        .filter(a -> a.keyType.equals(key) && a.valueType.equals(value))
                        .findFirst()
                        .orElseGet(() -> {
                            MapType typeInstance = new MapType(key, value);
                            INSTANCES.add(typeInstance);
                            return typeInstance;
                        });

    }

    @Override
    public String getTypeName() {
        return "Map[" + keyType.getTypeName() + "," + valueType.getTypeName() + "]";
    }
}
