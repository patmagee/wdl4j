package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.NamespaceElement;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "newBuilder")
public class Document extends AbstractNamespaceElement implements WdlElement, NamespaceElement {

    private Version version;
    private List<Import> imports;
    private List<Struct> structs;
    private List<Task> tasks;
    private Workflow workflow;
    private Map<String, Document> importedDocuments;

    public Document(Version version, List<Import> imports, List<Struct> structs, List<Task> tasks, Workflow workflow) {
        this.version = version;
        this.imports = imports;
        this.structs = structs;
        this.tasks = tasks;
        this.workflow = workflow;
    }

    @Override
    public WdlElement lookupElement(String name) {
        return null;
    }
}
