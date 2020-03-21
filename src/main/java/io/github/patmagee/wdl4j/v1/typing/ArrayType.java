package io.github.patmagee.wdl4j.v1.typing;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ArrayType extends Type {

    private Type innerType;
    private Boolean nonEmpty = false;

    @Override
    public String getTypeName() {
        return "Array[" + innerType.getTypeName() + "]" + (nonEmpty ? "+" : "");
    }

}
