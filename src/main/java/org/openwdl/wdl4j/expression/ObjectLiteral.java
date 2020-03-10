package org.openwdl.wdl4j.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObjectLiteral extends Expression {

    private Map<String,Expression> values;

    @Override
    public String getElementName() {
        return "Object";
    }

}
