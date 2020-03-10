package org.openwdl.wdl4j.v1.typing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArrayType extends Type {

    private Type innerType;
    private Boolean nonEmpty = false;

    @Override
    public String getElementName() {
        return "Array[" + innerType.getElementName() + "]";
    }
}
