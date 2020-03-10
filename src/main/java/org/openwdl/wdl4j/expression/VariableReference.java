package org.openwdl.wdl4j.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VariableReference extends Expression {

    private String name;

    @Override
    public String getElementName() {
        return name;
    }
}
