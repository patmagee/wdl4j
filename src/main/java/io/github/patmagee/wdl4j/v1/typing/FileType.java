package io.github.patmagee.wdl4j.v1.typing;

public class FileType extends Type {

    private static final FileType INSTANCE = new FileType();

    public static FileType getType() {
        return INSTANCE;
    }

    private FileType() {

    }

    @Override
    public String getTypeName() {
        return "File";
    }
}
