package org.openwdl.wdl4j;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openwdl.wdl4j.api.WdlElement;
import org.openwdl.wdl4j.expression.Expression;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Command implements WdlElement {

    private List<CommandPart> commandParts;

    @Override
    public String getElementName() {
        return "command";
    }

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

        @Override
        public String getElementName() {
            return "command part";
        }
    }

}
