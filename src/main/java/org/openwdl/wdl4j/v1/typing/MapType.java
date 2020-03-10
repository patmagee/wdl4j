package org.openwdl.wdl4j.v1.typing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapType extends Type {

    private Type keyType;
    private Type valueType;


    @Override
    public String getElementName() {
        return "Map[" + keyType.getElementName() + "," + valueType.getElementName() + "]";
    }
}
