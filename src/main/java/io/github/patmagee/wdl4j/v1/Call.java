package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.NamedElement;
import io.github.patmagee.wdl4j.v1.api.WorkflowElement;
import io.github.patmagee.wdl4j.v1.expression.Expression;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "newBuilder")
public class Call implements WorkflowElement, NamedElement {

    @NonNull
    private String taskName;
    private String callAlias;
    private Map<String, Expression> inputs;

    @Override
    public String getName() {
        return callAlias != null ? callAlias : taskName;
    }
}
