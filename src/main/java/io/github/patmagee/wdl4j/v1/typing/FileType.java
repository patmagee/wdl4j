package io.github.patmagee.wdl4j.v1.typing;

import lombok.NonNull;

public class FileType extends Type {

    private static final FileType INSTANCE = new FileType();

    public static FileType getType() {
        return INSTANCE;
    }

    private FileType() {

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
        return "File";
    }
}
