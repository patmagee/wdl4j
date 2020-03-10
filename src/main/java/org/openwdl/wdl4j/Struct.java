package org.openwdl.wdl4j;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openwdl.wdl4j.api.WdlElement;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Struct implements WdlElement {

    private String name;
    private List<Declaration> members;


    @Override
    public String getElementName() {
        return name;
    }
}
