package io.github.patmagee.wdl4j.v1.typing;

import lombok.NonNull;

public class BooleanType extends Type {

    private static final BooleanType INSTANCE = new BooleanType();

    public static BooleanType getType() {
        return INSTANCE;
    }

    private BooleanType() {
    }

    @Override
    public boolean isCoercibleTo(@NonNull Type toType) {
        if (toType instanceof StringType){
            return true;
        } else  {
            return super.isCoercibleTo(toType);
        }
    }

    @Override
    public String getTypeName() {
        return "Boolean";
    }
}
