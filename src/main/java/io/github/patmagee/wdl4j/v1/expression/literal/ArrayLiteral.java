package io.github.patmagee.wdl4j.v1.expression.literal;

import io.github.patmagee.wdl4j.v1.expression.Expression;

import java.util.Collections;
import java.util.List;

public class ArrayLiteral extends Expression {

    private final List<Expression> values;

    public ArrayLiteral(List<Expression> values) {
        if (values == null) {
            values = Collections.emptyList();
        }
        this.values = values;
    }

    public List<Expression> getValues() {
        return values;
    }
}
