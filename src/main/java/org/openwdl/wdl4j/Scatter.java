package org.openwdl.wdl4j;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openwdl.wdl4j.api.WorkflowElement;
import org.openwdl.wdl4j.expression.Expression;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Scatter implements WorkflowElement {

    public String varname;
    public Expression expression;
    public List<Declaration> declarations;
    public List<WorkflowElement> workflowElements;

    @Override
    public String getElementName() {
        return "scatter";
    }

}
