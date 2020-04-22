package io.github.patmagee.wdl4j.v1.expression.literal;

import io.github.patmagee.wdl4j.v1.expression.Expression;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
public class MapLiteral extends Expression {

    @NonNull
    private final List<MapEntry> values;

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
