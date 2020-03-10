package org.openwdl.wdl4j;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openwdl.wdl4j.api.WorkflowElement;
import org.openwdl.wdl4j.expression.Expression;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Call implements WorkflowElement {

    public String taskName;
    public String callAlias;
    public Map<String, Expression> inputs;

    @Override
    public String getElementName() {
        return "call " + taskName;
    }
}
