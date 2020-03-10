package org.openwdl.wdl4j;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openwdl.wdl4j.api.WdlElement;
import org.openwdl.wdl4j.api.WorkflowElement;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Workflow implements WdlElement {

    private String name;
    private Inputs inputs;
    private List<Declaration> declarations;
    private List<WorkflowElement> elements;
    private Outputs outputs;
    private Meta meta;
    private ParameterMeta parameterMeta;

    @Override
    public String getElementName() {
        return "workflow  " + name;
    }
}
