package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
public class BooleanType implements Type {

    private static final BooleanType INSTANCE = new BooleanType();

    private BooleanType(){}

    @Override
    public String getTypeName() {
        return "Boolean";
    }

    public static BooleanType getType(){
        return INSTANCE;
    }
}
