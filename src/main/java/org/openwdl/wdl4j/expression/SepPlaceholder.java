package org.openwdl.wdl4j.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SepPlaceholder extends Expression {

    private Expression value;

    @Override
    public String getElementName() {
        return "Sep Placeholder";
    }
}
