package io.github.patmagee.wdl4j.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.api.WorkflowElement;

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

}
