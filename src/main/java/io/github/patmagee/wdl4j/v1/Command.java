package io.github.patmagee.wdl4j.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.expression.Expression;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Command implements WdlElement {

    private List<CommandPart> commandParts;

    @Getter
    @NoArgsConstructor
    public static class CommandPart implements WdlElement{

        private String stringPart;
        private List<Expression> placeholders;
        private Expression expression;

        public CommandPart(String stringPart) {
            this.stringPart = stringPart;
            this.placeholders = null;
            this.expression = null;
        }

        public CommandPart(List<Expression> placeholders,Expression expression) {
            this.stringPart = null;
            this.placeholders = placeholders;
            this.expression = expression;
        }

    }

}
