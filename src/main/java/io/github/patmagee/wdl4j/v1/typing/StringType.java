package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
public class StringType implements Type {

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
