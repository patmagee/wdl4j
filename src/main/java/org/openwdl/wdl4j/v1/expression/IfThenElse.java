package org.openwdl.wdl4j.v1.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IfThenElse extends Expression {

    private Expression condition;
    private Expression ifTrue;
    private Expression ifFalse;

    @Override
    public String getElementName() {
        return "if then else";
    }
}
