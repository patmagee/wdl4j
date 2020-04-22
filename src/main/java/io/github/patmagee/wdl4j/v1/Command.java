package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.expression.Expression;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "newBuilder")
public class Command implements WdlElement {

    private List<CommandPart> commandParts;

    @Data
    public static class CommandPart implements WdlElement {

        private String stringPart;
        private List<Expression> placeholders;
        private Expression expression;

        public CommandPart(String stringPart) {
            this.stringPart = stringPart;
            this.placeholders = null;
            this.expression = null;
        }

        public CommandPart(List<Expression> placeholders, Expression expression) {
            this.stringPart = null;
            this.placeholders = placeholders;
            this.expression = expression;
        }
    }
}
