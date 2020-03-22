package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class FloatType implements Type {

    private static final FloatType INSTANCE = new FloatType();

    private FloatType(){

    }

    public static FloatType getType() {
        return INSTANCE;
    }

    @Override
    public String getTypeName() {
        return "Float";
    }
}
