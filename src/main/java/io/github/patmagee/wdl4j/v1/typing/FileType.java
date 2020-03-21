package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode
public class FileType extends Type {

    @Override
    public String getTypeName() {
        return "File";
    }
}
