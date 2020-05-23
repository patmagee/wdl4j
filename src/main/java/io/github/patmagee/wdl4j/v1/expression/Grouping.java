package io.github.patmagee.wdl4j.v1.expression;

import io.github.patmagee.wdl4j.v1.Namespace;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.Type;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Grouping extends Expression {

    Expression innerExpression;

    public Grouping(@NonNull Expression innerExpression, @NonNull int id) {
        super(id);
        this.innerExpression = innerExpression;
    }

    @Override
    public Type typeCheck(WdlElement target, Namespace namespace) throws WdlValidationError {
        return innerExpression.typeCheck(target,namespace);
    }
}
