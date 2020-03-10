package org.openwdl.wdl4j.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Negate extends Expression {

    private Expression expression;

    @Override
    public String getElementName() {
        return "Not";
    }
}
