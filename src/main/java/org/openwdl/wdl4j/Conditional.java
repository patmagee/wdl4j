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
public class Conditional implements WorkflowElement {

    private Expression expression;
    private List<Declaration> declarations;
    private List<WorkflowElement> elements;

    @Override
    public String getElementName() {
        return "conditional";

    }
}
