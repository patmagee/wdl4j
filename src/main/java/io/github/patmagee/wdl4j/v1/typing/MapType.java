package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

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

    @Override
    public String getTypeName() {
        return "Map[" + keyType.getTypeName() + "," + valueType.getTypeName() + "]";
    }

    private final static Set<MapType> INSTANCES = new HashSet<>();

    public static MapType getType(@NonNull Type keyType,@NonNull Type valueType) {
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
