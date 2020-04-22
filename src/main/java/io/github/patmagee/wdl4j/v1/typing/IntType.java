package io.github.patmagee.wdl4j.v1.typing;

public class IntType extends Type {

    private static final IntType INSTANCE = new IntType();

    public static IntType getType() {
        return INSTANCE;
    }

    private IntType() {

    }

    @Override
    public String getTypeName() {
        return "Int";
    }
}
