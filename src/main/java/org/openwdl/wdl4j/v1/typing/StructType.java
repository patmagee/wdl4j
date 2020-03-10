package org.openwdl.wdl4j.v1.typing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StructType extends Type {

    private String name;


    @Override
    public String getElementName() {
        return name;
    }
}
