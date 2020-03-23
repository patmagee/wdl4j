package io.github.patmagee.wdl4j.v1.typing;

public class StringType extends Type {

    private static final StringType INSTANCE = new StringType();

    private StringType() {

    }

    public static StringType getType() {
        return INSTANCE;
    }

    @Override
    public String getTypeName() {
        return "String";
    }
}
