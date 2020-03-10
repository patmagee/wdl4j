package org.openwdl.wdl4j.typing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionalType extends Type {

    private Type innerType;

    @Override
    public String getElementName() {
        return "Optional";
    }
}
