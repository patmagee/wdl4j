package org.openwdl.wdl4j.typing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PairType extends Type {

    private Type leftType;
    private Type rightType;


    @Override
    public String getElementName() {
        return "Pair[" + leftType.getElementName() + "," + rightType.getElementName() + "]";
    }
}
