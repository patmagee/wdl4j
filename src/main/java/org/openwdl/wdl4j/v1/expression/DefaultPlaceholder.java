package org.openwdl.wdl4j.v1.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultPlaceholder extends Expression {

    private Expression value;

    @Override
    public String getElementName() {
        return "Default Placeholder";
    }
}
