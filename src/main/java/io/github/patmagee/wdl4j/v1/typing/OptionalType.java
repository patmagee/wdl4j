package io.github.patmagee.wdl4j.v1.typing;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OptionalType extends Type {

    private Type innerType;

    @Override
    public String getTypeName() {
        return innerType.getTypeName() + "?";
    }
}
