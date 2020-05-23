package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.StandardLib;
import io.github.patmagee.wdl4j.v1.exception.NamespaceException;
import lombok.Setter;

import java.util.*;

public class Namespace {

    @Setter
    private Namespace parent;
    private Map<String, Namespace> children = new HashMap<>();
    private Set<Struct> structs = new HashSet<>();
    private Map<String, Declaration> elements = new HashMap<>();
    @Setter
    private StandardLib lib;

    public void addChildNamespace(String name, Namespace child) throws NamespaceException {
        assertNotDefinedInNamespace(name,
                                    "Namespace collision, an element in the Namespace with the name \"" + name + "\" already exists");
        children.put(name, child);
    }

    public void addDeclaration(String name, Declaration elementType) throws NamespaceException {
        assertNotDefinedInNamespace(name,
                                    "Namespace collision, an element in the Namespace with the name \"" + name + "\" already exists");

        elements.put(name,
                     new Declaration(elementType.getDeclType(), elementType.getName(), null, elementType.getId()));
    }

    public void addStruct(Struct struct) throws NamespaceException {
        assertNotDefinedInNamespace(struct.getName(),
                                    "Namespace collision, an element in the Namespace with the name \"" + struct.getName() + "\" already exists");
        structs.add(struct);
    }

    public boolean isDefinedInNamespace(String name) {
        return (parent != null && parent.isDefinedInNamespace(name)) || children.containsKey(name) || elements.containsKey(
                name) || structs.stream().anyMatch(s -> s.getName().equals(name));
    }

    public void assertNotDefinedInNamespace(String name, String errorMessage) throws NamespaceException {
        if (isDefinedInNamespace(name)) {
            throw new NamespaceException(errorMessage);
        }
    }

    public Declaration getDeclaration(String name) throws NamespaceException {
        return Optional.ofNullable(elements.get(name))
                       .orElseThrow(() -> new NamespaceException("Declaration with name \"" + name + "\" is not decined in the current namespace"));
    }

    public Struct getStruct(String name) throws NamespaceException {
        return structs.stream()
                      .filter(struct -> struct.getName().equals(name))
                      .findFirst()
                      .orElseThrow(() -> new NamespaceException("Struct with name \"" + name + "\" is not decined in the current namespace"));
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
