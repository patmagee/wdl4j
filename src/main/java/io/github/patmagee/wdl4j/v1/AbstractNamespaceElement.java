package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.NamespaceElement;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNamespaceElement implements NamespaceElement {

    @Setter
    @Getter
    private NamespaceElement parentNamespace;

    @Getter
    private List<NamespaceElement> childNamespaces;

    @Override
    public void addChildNamespace(NamespaceElement child) {
        if (childNamespaces == null) {
            childNamespaces = new ArrayList<>();
        }

        childNamespaces.add(child);
    }

    @Override
    public WdlElement lookupElement(String name) {
        if (parentNamespace != null) {
            return parentNamespace.lookupElement(name);
        }
        return null;
    }
}
