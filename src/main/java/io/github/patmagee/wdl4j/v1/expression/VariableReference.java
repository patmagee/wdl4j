package io.github.patmagee.wdl4j.v1.expression;

import io.github.patmagee.wdl4j.v1.api.NamedElement;
import lombok.NonNull;
import lombok.Value;

@Value
public class VariableReference extends Expression implements NamedElement {

    @NonNull
    private final String name;

}
