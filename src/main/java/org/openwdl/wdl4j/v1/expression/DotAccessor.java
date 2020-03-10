package org.openwdl.wdl4j.v1.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DotAccessor extends Expression {

    private Expression element;
    private String name;

    @Override
    public String getElementName() {
        return "getName";
    }
}
