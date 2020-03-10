package org.openwdl.wdl4j.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openwdl.wdl4j.v1.api.WdlElement;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inputs implements WdlElement {

    private List<Declaration> declarations;


    @Override
    public String getElementName() {
        return "input";
    }
}
