package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.api.WorkflowElement;
import io.github.patmagee.wdl4j.v1.expression.Expression;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "newBuilder")
public class Scatter implements WorkflowElement {

    @NonNull
    private String varname;

    @NonNull
    private Expression expression;
    private List<Declaration> declarations;
    private List<WdlElement> workflowElements;
    @NonNull
    private int id;

}
