package io.github.patmagee.wdl4j.v1.api;

public interface NamespaceElement {

    void setParentNamespace(NamespaceElement parent);

    void addChildNamespace(NamespaceElement child);

    WdlElement lookupElement(String name);

}
