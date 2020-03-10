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
public class Document implements WdlElement {

    private Version version;
    private List<Import> imports;
    private List<Struct> structs;
    private List<Task> tasks;
    private Workflow workflow;

    @Override
    public String getElementName() {
        return "document";
    }
}
