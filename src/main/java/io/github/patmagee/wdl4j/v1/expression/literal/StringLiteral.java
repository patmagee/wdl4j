package io.github.patmagee.wdl4j.v1.expression.literal;

import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.expression.Expression;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Value
public class StringLiteral extends Expression {

    @NonNull
    public final List<StringPart> stringParts;

    @Getter
    @ToString
    @EqualsAndHashCode
    public static class StringPart implements WdlElement {

        private final String stringPart;
        private final Expression expression;
        private final List<Expression> placeholders;

        public StringPart(String stringPart) {
            Objects.requireNonNull(stringPart, "The string of a StringPart cannot be null");
            this.stringPart = stringPart;
            this.expression = null;
            this.placeholders = null;
        }

        public StringPart(List<Expression> placeholders, Expression expression) {
            Objects.requireNonNull(expression, "the Expression of StringPart cannot be null");
            this.stringPart = null;
            this.placeholders = placeholders;
            this.expression = expression;
        }
    }

    public StringLiteral(List<StringPart> stringParts) {
        if (stringParts == null) {
            stringParts = Collections.emptyList();
        }
        this.stringParts = stringParts;
    }
}
