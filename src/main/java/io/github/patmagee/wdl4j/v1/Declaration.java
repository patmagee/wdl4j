package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.expression.Expression;
import io.github.patmagee.wdl4j.v1.typing.Type;

import java.util.Objects;

public class Declaration implements WdlElement {

    private final Type declType;
    private final String name;
    private final Expression expression;

    public Declaration(Type declType, String name) {
        this(declType, name, null);
    }

    public Declaration(Type declType, String name, Expression expression) {
        Objects.requireNonNull(declType, "The type of a declaration cannot be null");
        Objects.requireNonNull(name, "The name of a declaration cannot be null");
        this.declType = declType;
        this.name = name;
        this.expression = expression;
    }

    public Type getDeclType() {
        return declType;
    }

    public String getName() {
        return name;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Declaration that = (Declaration) o;
        return declType.equals(that.declType) && name.equals(that.name) && Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(declType, name, expression);
    }
}
