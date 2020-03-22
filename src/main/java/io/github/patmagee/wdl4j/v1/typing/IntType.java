package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
public class IntType implements Type {

    private static final IntType INSTANCE = new IntType();

    private IntType(){

    }

    public static IntType getType() {
        return INSTANCE;
    }

    @Override
    public String getTypeName() {
        return "Int";
    }
}
