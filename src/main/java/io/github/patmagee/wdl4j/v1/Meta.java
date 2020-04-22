package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.expression.Expression;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "newBuilder")
public class Meta implements WdlElement {

    private Map<String, Expression> attributes;

}
