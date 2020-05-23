package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.StandardLib;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.NamespaceException;
import io.github.patmagee.wdl4j.v1.exception.WdlValidationError;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "newBuilder")
public class Document extends AbstractNamespaceElement implements WdlElement {

    private Version version;
    private List<Import> imports;
    private List<Struct> structs;
    private List<Task> tasks;
    private Workflow workflow;
    private Map<String, Document> importedDocuments;
    @NonNull
    private int id;
    private StandardLib lib;

    public Document(Version version, List<Import> imports, List<Struct> structs, List<Task> tasks, Workflow workflow,int id) {
        this.version = version;
        this.imports = imports;
        this.structs = structs;
        this.tasks = tasks;
        this.workflow = workflow;
        this.id = id;
    }

    public void typeCheck() throws WdlValidationError {
        if (importedDocuments != null) {
            for (Map.Entry<String, Document> importedDocument : importedDocuments.entrySet()) {
                importedDocument.getValue().typeCheck();
            }
        }

        Namespace namespace = captureNamespace();
        if (workflow != null) {
            workflow.typeCheck(namespace);
        }

        if (tasks != null) {
            for (Task task : tasks) {
                task.typeCheck(namespace);
            }
        }
    }

    private Namespace captureNamespace() throws NamespaceException {
        Namespace namespace = new Namespace();
        namespace.setLib(lib);
        if (importedDocuments != null) {
            for (Map.Entry<String, Document> entry : importedDocuments.entrySet()) {
                String childNamespace = entry.getKey();
                Document childDocument = entry.getValue();
                captureChildNamespace(namespace, childNamespace, childDocument);
            }
        }

        if (workflow != null) {
            captureWorfklowNamespace(namespace);
        }

        if (tasks != null) {
            for (Task task : tasks) {
                captureTaskNamespace(namespace, task);
            }
        }
        return namespace;
    }

    private void captureChildNamespace(Namespace currentNamespace, String childNamespace, Document childDocument) throws NamespaceException {
        currentNamespace.addChildNamespace(childNamespace, childDocument.captureNamespace());
        Import docImport = imports.stream().filter(imp -> imp.getName().equals(childNamespace)).findFirst().get();
        List<Struct> importedStructs = childDocument.getStructs(docImport.getAliases());
        for (Struct struct : importedStructs) {
            currentNamespace.addStruct(struct);
        }
    }

    private void captureTaskNamespace(Namespace currentNamespace, Task task) throws NamespaceException {
        Namespace taskNamespace = new Namespace();
        if (task.getInputs() != null && task.getInputs().getDeclarations() != null) {
            for (Declaration inputDecl : task.getInputs().getDeclarations()) {
                taskNamespace.addDeclaration(inputDecl.getName(), inputDecl);
            }
        }

        if (task.getOutputs() != null && task.getOutputs().getDeclarations() != null) {
            for (Declaration outputDecl : task.getOutputs().getDeclarations()) {
                taskNamespace.addDeclaration(outputDecl.getName(), outputDecl);
            }
        }

        currentNamespace.addChildNamespace(task.getName(), taskNamespace);
    }

    private void captureWorfklowNamespace(Namespace currentNamespace) throws NamespaceException {
        Namespace workflowNamespace = new Namespace();
        if (workflow.getInputs() != null && workflow.getInputs().getDeclarations() != null) {
            for (Declaration inputDecl : workflow.getInputs().getDeclarations()) {
                workflowNamespace.addDeclaration(inputDecl.getName(), inputDecl);
            }
        }

        if (workflow.getOutputs() != null && workflow.getOutputs().getDeclarations() != null) {
            for (Declaration outputDecl : workflow.getOutputs().getDeclarations()) {
                workflowNamespace.addDeclaration(outputDecl.getName(), outputDecl);
            }
        }

        currentNamespace.addChildNamespace(workflow.getName(), workflowNamespace);
    }

    public List<Struct> getStructs(List<Import.ImportAlias> aliases) {
        List<Struct> structsToReturn = new ArrayList<>();
        if (structs != null) {
            for (Struct struct : structs) {
                Optional<Import.ImportAlias> optionalAlias = aliases == null
                                                             ? Optional.empty()
                                                             : aliases.stream()
                                                                      .filter(alias -> alias.getName()
                                                                                            .equals(struct.getName()) && alias.getAlias() != null)
                                                                      .findFirst();
                if (optionalAlias.isPresent()) {
                    Import.ImportAlias alias = optionalAlias.get();
                    structsToReturn.add(Struct.newBuilder()
                                              .members(struct.getMembers())
                                              .name(alias.getAlias())
                                              .build());
                } else {
                    structsToReturn.add(struct);
                }
            }
        }
        return structsToReturn;
    }
}
