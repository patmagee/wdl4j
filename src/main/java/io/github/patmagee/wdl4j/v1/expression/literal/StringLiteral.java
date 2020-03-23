package io.github.patmagee.wdl4j.v1.expression.literal;

import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.expression.Expression;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StringLiteral extends Expression {

    public final List<StringPart> stringParts;

    public StringLiteral(List<StringPart> stringParts) {
        if (stringParts == null) {
            stringParts = Collections.emptyList();
        }
        this.stringParts = stringParts;
    }

    public List<StringPart> getStringParts() {
        return stringParts;
    }

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

        public String getStringPart() {
            return stringPart;
        }

        public Expression getExpression() {
            return expression;
        }

        public List<Expression> getPlaceholders() {
            return placeholders;
        }
    }
}
