package io.github.patmagee.wdl4j.v1.expression.literal;

import io.github.patmagee.wdl4j.v1.expression.Expression;

import java.util.Collections;
import java.util.Map;

public class ObjectLiteral extends Expression {

    private final Map<String,Expression> values;

    public ObjectLiteral(Map<String, Expression> values) {
        if (values == null){
            values = Collections.emptyMap();
        }
        this.values = values;
    }

    public Map<String, Expression> getValues() {
        return values;
    }
}
