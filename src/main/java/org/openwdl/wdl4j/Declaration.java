package org.openwdl.wdl4j;

import lombok.*;
import org.openwdl.wdl4j.api.WdlElement;
import org.openwdl.wdl4j.expression.Expression;
import org.openwdl.wdl4j.typing.Type;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Declaration implements WdlElement {

    private Type declType;
    private String name;
    private Expression expression;

    public Declaration(@NonNull Type declType, @NonNull String name) {
        this(declType, name, null);
    }


    @Override
    public String getElementName() {
        return declType.getElementName() + " " + name;
    }
}
