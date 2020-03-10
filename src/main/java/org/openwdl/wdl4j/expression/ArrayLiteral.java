package org.openwdl.wdl4j.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArrayLiteral extends Expression {

    private List<Expression> values;

    @Override
    public String getElementName() {
        return "Array";
    }
}
