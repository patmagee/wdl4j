package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ObjectType implements Type {

    private static final ObjectType INSTANCE = new ObjectType();

    private ObjectType() {

    }

    public static ObjectType getType() {
        return INSTANCE;
    }

    @Override
    public String getTypeName() {
        return "Object";
    }
}
