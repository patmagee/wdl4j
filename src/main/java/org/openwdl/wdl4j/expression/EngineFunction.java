package org.openwdl.wdl4j.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EngineFunction extends Expression {

    private String name;
    private List<Expression> arguments;


    @Override
    public String getElementName() {
        return name;
    }
}
