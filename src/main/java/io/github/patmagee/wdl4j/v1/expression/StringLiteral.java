package io.github.patmagee.wdl4j.v1.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import io.github.patmagee.wdl4j.v1.api.WdlElement;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StringLiteral extends Expression {

    public List<StringPart> stringParts;

    @Getter
    @NoArgsConstructor
    public static class StringPart implements WdlElement {

        private String stringPart;
        private Expression expression;
        private List<Expression> placeholders;

        public StringPart(String stringPart) {
            this.stringPart = stringPart;
            this.expression = null;
        }

        public StringPart(List<Expression> placeholders, Expression expression) {
            this.stringPart = null;
            this.placeholders = placeholders;
            this.expression = expression;
        }

    }
}