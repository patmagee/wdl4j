package io.github.patmagee.wdl4j.v1;

import lombok.Getter;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.ExistingReferenceInNamespace;
import io.github.patmagee.wdl4j.v1.exception.NoSuchReferenceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public class Namespace {

    public final static String GLOBAL_NAMESPACE = "global";

    private String name;
    private Map<String, WdlElement> elements = new HashMap<>();
    private Namespace parent;

    public Namespace(String name) {
        this.name = name;
        this.parent = null;
    }

    public Namespace(Namespace parent, String name) {
        this.name = name;
        this.parent = parent;
    }

    public void setParent(Namespace namespace) {
        this.parent = namespace;
    }

    public void addElementInNamespace(String identifier, WdlElement element) {
        WdlElement existingElement = elements.get(identifier);
        if (existingElement != null && !element.equals(existingElement)) {
            throw new ExistingReferenceInNamespace("An alement already exists in the current namespace for identifier: " + identifier);
        }

        elements.putIfAbsent(identifier, element);
    }

    public WdlElement resolveReference(String referenceString) {
        Optional<WdlElement> reference = Optional.empty();
        if (parent != null) {
            reference = Optional.ofNullable(parent.resolveReference(referenceString));
        }
        return reference.orElseGet(() -> elements.get(referenceString));
    }

    public WdlElement resolveReferenceExceptionally(String referenceString) {
        WdlElement reference = resolveReference(referenceString);
        if (reference == null) {
            throw new NoSuchReferenceException("Could not identify a reference: " + referenceString + " in the current or parent namespaces");
        }
        return reference;
    }

}
