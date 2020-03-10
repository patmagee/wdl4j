package org.openwdl.wdl4j.v1.typing;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class BooleanType extends Type {

    @Override
    public String getElementName() {
        return "Boolean";
    }
}
