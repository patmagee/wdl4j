package org.openwdl.wdl4j;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openwdl.wdl4j.api.WdlElement;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task implements WdlElement {


    private String name;
    private Inputs inputs;
    private List<Declaration> declarations;
    private Command command;
    private Runtime runtime;
    private Outputs outputs;
    private Meta meta;
    private ParameterMeta parameterMeta;


    @Override
    public String getElementName() {
        return "task " + name;
    }
}
