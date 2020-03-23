package io.github.patmagee.wdl4j.v1.typing;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MapType extends Type {

    private final Type keyType;
    private final Type valueType;

    private MapType(Type keyType, Type valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }

    public Type getKeyType() {
        return keyType;
    }

    public Type getValueType() {
        return valueType;
    }

    @Override
    public String getTypeName() {
        return "Map[" + keyType.getTypeName() + "," + valueType.getTypeName() + "]";
    }

    private final static Set<MapType> INSTANCES = new HashSet<>();

    public static MapType getType(Type keyType, Type valueType) {
        Objects.requireNonNull(keyType, "keyType of a MapType cannot be null");
        Objects.requireNonNull(valueType, "valueType of a MapType cannot be null");
        for (MapType instance : INSTANCES) {
            if (instance.valueType.equals(valueType) && instance.keyType.equals(keyType)) {
                return instance;
            }
        }

        MapType instance = new MapType(keyType, valueType);
        INSTANCES.add(instance);
        return instance;
    }

}
