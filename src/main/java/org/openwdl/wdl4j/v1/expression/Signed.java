package org.openwdl.wdl4j.v1.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Signed extends Expression {

    private Operation operation;
    private Expression expression;

    @Override
    public String getElementName() {
        return "Signed";
    }


    public enum Operation {
        PLUS,MINUS
    }
}
