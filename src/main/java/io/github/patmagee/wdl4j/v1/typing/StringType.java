package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
public class StringType extends Type {

    @Override
    public String getTypeName() {
        return "String";
    }
}
