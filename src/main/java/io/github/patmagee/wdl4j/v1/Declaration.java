package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.NamedElement;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.expression.Expression;
import io.github.patmagee.wdl4j.v1.typing.Type;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder(builderMethodName = "newBuilder")
public class Declaration implements WdlElement, NamedElement {

    @NonNull
    private Type declType;
    @NonNull
    private String name;
    private Expression expression;

}
