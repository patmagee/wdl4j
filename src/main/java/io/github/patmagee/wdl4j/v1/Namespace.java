package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.StandardLib;
import io.github.patmagee.wdl4j.v1.exception.NamespaceException;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class Namespace {

    @Setter
    private Namespace parent;
    private final Map<String, Namespace> children = new HashMap<>();
    private final Map<String, Struct> structs = new HashMap<>();
    private final Map<String, Declaration> elements = new HashMap<>();
    @Setter
    private StandardLib lib;

    public void addChildNamespace(String name, Namespace child) throws NamespaceException {
        assertNotDefinedInNamespace(name,
                                    "Namespace collision, an element in the Namespace with the name \"" + name + "\" already exists");
        children.put(name, child);
    }

    public void addDeclaration(String name, Declaration declaration) throws NamespaceException {
        assertNotDefinedInNamespace(name,
                                    "Namespace collision, an element in the Namespace with the name \"" + name + "\" already exists");

        elements.put(name,
                     new Declaration(declaration.getDeclType(), declaration.getName(), declaration.getExpression(), declaration.getId()));
    }

    public void addStruct(Struct struct) throws NamespaceException {
        assertNotDefinedInNamespace(struct.getName(),
                                    "Namespace collision, an element in the Namespace with the name \"" + struct.getName() + "\" already exists");
        structs.put(struct.getName(), struct);
    }

    public boolean isDefinedInNamespace(String name) {
        return isDefinedInParentNamesapce(name) || isDefininedInLocalNamespace(name);
    }

    private boolean isDefininedInLocalNamespace(String name) {
        return children.containsKey(name) || elements.containsKey(name) || structs.containsKey(name);
    }

    private boolean isDefinedInParentNamesapce(String name) {
        return parent != null && parent.isDefinedInNamespace(name);
    }

    public void assertNotDefinedInNamespace(String name, String errorMessage) throws NamespaceException {
        if (!isDefinedInNamespace(name)) {
            throw new NamespaceException(errorMessage);
        }
    }

    public Declaration getDeclaration(String name) throws NamespaceException {
        if (isDefinedInParentNamesapce(name)) {
            return parent.getDeclaration(name);
        }

        if (isDefininedInLocalNamespace(name)) {
            return elements.get(name);
        } else {
            throw new NamespaceException("Declaration with name \"" + name + "\" is not decined in the current namespace");
        }
    }

    public Struct getStruct(String name) throws NamespaceException {
        if (isDefinedInParentNamesapce(name)) {
            return parent.getStruct(name);
        }

        if (isDefininedInLocalNamespace(name)) {
            return structs.get(name);
        } else {
            throw new NamespaceException("Struct with name \"" + name + "\" is not decined in the current namespace");
        }
    }

    public StandardLib getLib() throws NamespaceException {
        if (lib != null) {
            return lib;
        } else if (parent != null) {
            return parent.getLib();
        } else {
            throw new NamespaceException("No standard lib is currently defined in this namespace");
        }
    }

}
