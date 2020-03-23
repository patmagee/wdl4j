package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;

import java.util.List;
import java.util.Map;

public class Document implements WdlElement {

    private final Version version;
    private final List<Import> imports;
    private final List<Struct> structs;
    private final List<Task> tasks;
    private final Workflow workflow;

    private Map<String, Document> importedDocuments;



    public static Builder newBuilder() {
        return new Builder();
    }

    public Document(Version version, List<Import> imports, List<Struct> structs, List<Task> tasks, Workflow workflow) {
        this.version = version;
        this.imports = imports;
        this.structs = structs;
        this.tasks = tasks;
        this.workflow = workflow;
    }

    private Document(Builder builder) {
        version = builder.version;
        imports = builder.imports;
        structs = builder.structs;
        tasks = builder.tasks;
        workflow = builder.workflow;
        setImportedDocuments(builder.importedDocuments);
    }

    public Version getVersion() {
        return version;
    }

    public List<Import> getImports() {
        return imports;
    }

    public List<Struct> getStructs() {
        return structs;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public Map<String, Document> getImportedDocuments() {
        return importedDocuments;
    }

    public void setImportedDocuments(Map<String, Document> importedDocuments) {
        this.importedDocuments = importedDocuments;
    }

    public static final class Builder {

        private Version version;
        private List<Import> imports;
        private List<Struct> structs;
        private List<Task> tasks;
        private Workflow workflow;
        private Map<String, Document> importedDocuments;

        private Builder() {
        }

        public Builder withVersion(Version val) {
            version = val;
            return this;
        }

        public Builder withImports(List<Import> val) {
            imports = val;
            return this;
        }

        public Builder withStructs(List<Struct> val) {
            structs = val;
            return this;
        }

        public Builder withTasks(List<Task> val) {
            tasks = val;
            return this;
        }

        public Builder withWorkflow(Workflow val) {
            workflow = val;
            return this;
        }

        public Builder withImportedDocuments(Map<String, Document> val) {
            importedDocuments = val;
            return this;
        }

        public Document build() {
            return new Document(this);
        }
    }

}
