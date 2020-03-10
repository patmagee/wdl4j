package org.openwdl.wdl4j;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openwdl.wdl4j.api.WdlElement;
import org.openwdl.wdl4j.expression.Expression;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Import implements WdlElement {


    private Expression url;
    private String name;
    private List<ImportAlias> aliases;

    @Override
    public String getElementName() {
        return "import";
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImportAlias implements WdlElement {
        private String name;
        private String alias;

        @Override
        public String getElementName() {
            return "import alias";
        }
    }
}
