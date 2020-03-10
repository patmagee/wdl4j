package org.openwdl.wdl4j.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotEqualTo extends Expression {

    private Expression leftHandSide;
    private Expression rightHandSide;

    @Override
    public String getElementName() {
        return "not equal to";
    }
}
