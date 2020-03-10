package org.openwdl.wdl4j;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openwdl.wdl4j.api.WdlElement;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Version implements WdlElement {

    private String release;

    @Override
    public String getElementName() {
        return "version " + release;
    }
}
