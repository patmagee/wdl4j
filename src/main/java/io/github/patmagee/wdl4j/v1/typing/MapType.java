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
public class MapType extends Type {

    private final static Set<MapType> INSTANCES = new HashSet<>();
    @NonNull
    private final Type keyType;
    @NonNull
    private final Type valueType;

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

    @Override
    public String getTypeName() {
        return "Map[" + keyType.getTypeName() + "," + valueType.getTypeName() + "]";
    }

}
