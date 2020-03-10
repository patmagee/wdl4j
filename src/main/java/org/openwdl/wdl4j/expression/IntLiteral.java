package org.openwdl.wdl4j.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntLiteral extends Expression {

    private Integer value;

    @Override
    public String getElementName() {
        return "Int";
    }
}
