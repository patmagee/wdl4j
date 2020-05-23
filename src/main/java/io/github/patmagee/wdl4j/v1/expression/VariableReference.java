package io.github.patmagee.wdl4j.v1.expression;

import io.github.patmagee.wdl4j.v1.Declaration;
import io.github.patmagee.wdl4j.v1.Namespace;
import io.github.patmagee.wdl4j.v1.api.NamedElement;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.IllegalReferenceException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import io.github.patmagee.wdl4j.v1.typing.Type;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class VariableReference extends Expression implements NamedElement {

    String name;

    public VariableReference(@NonNull String name, @NonNull int id) {
        super(id);
        this.name = name;
    }

    @Override
    public Type typeCheck(WdlElement target, Namespace namespace) throws WdlValidationError {
        Declaration declaration = namespace.getDeclaration(name);
        if (target.getId() > declaration.getId()) {
            throw new IllegalReferenceException("Variable \"" + declaration.getName() + "\" referenced prior to being defned");
        }
        return declaration.getDeclType();

    }
}
