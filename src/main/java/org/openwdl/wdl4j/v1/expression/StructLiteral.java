package org.openwdl.wdl4j.v1.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StructLiteral extends Expression {

    private String name;
    private Map<String, Expression> values;

    @Override
    public String getElementName() {
        return name;
    }
}
