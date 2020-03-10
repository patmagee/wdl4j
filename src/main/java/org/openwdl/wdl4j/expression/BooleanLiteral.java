package org.openwdl.wdl4j.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BooleanLiteral extends Expression {

    private Boolean value;

    @Override
    public String getElementName() {
        return "Boolean";
    }
}
