package org.openwdl.wdl4j;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openwdl.wdl4j.api.WdlElement;
import org.openwdl.wdl4j.expression.Expression;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Runtime implements WdlElement {

    private Map<String, Expression> attributes;

    @Override
    public String getElementName() {
        return "runtime";
    }
}
