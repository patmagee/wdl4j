package io.github.patmagee.wdl4j.v1.expression.literal;

import io.github.patmagee.wdl4j.v1.expression.Expression;

import java.util.Collections;
import java.util.List;

public class MapLiteral extends Expression {

    private final List<MapEntry> values;

    public MapLiteral(List<MapEntry> values) {
        if (values == null){
            values = Collections.emptyList();
        }
        this.values = values;
    }

    public List<MapEntry> getValues() {
        return values;
    }

    public static class MapEntry {
        final Expression key;
        final Expression value;

        public MapEntry(Expression key, Expression value) {
            this.key = key;
            this.value = value;
        }

        public Expression getKey() {
            return key;
        }

        public Expression getValue() {
            return value;
        }
    }
}
