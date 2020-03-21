package io.github.patmagee.wdl4j.v1.typing;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode
public class IntType extends Type {

    @Override
    public String getTypeName() {
        return "Int";
    }
}