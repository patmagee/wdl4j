package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.expression.Expression;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Command implements WdlElement {

    private final List<CommandPart> commandParts;

    public static Builder newBuilder() {
        return new Builder();
    }

    public Command(List<CommandPart> commandParts) {
        if (commandParts == null) {
            commandParts = Collections.emptyList();
        }
        this.commandParts = commandParts;
    }

    private Command(Builder builder) {
        commandParts = builder.commandParts;
    }

    public List<CommandPart> getCommandParts() {
        return commandParts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Command command = (Command) o;
        return Objects.equals(commandParts, command.commandParts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandParts);
    }

    public static class CommandPart implements WdlElement {

        private final String stringPart;
        private final List<Expression> placeholders;
        private final Expression expression;

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

        public String getStringPart() {
            return stringPart;
        }

        public List<Expression> getPlaceholders() {
            return placeholders;
        }

        public Expression getExpression() {
            return expression;
        }
    }

    public static final class Builder {

        private List<CommandPart> commandParts;

        private Builder() {
        }

        public Builder withCommandParts(List<CommandPart> val) {
            commandParts = val;
            return this;
        }

        public Command build() {
            return new Command(this);
        }
    }

}
