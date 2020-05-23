package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.NamedElement;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.NamespaceException;
import io.github.patmagee.wdl4j.v1.util.UriUtils;
import lombok.*;

import java.net.URI;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "newBuilder")
public class Import implements WdlElement, NamedElement {

    @NonNull
    private String url;
    private String name;
    private List<ImportAlias> aliases;
    @NonNull
    private int id;

    @Data
    public static class ImportAlias implements WdlElement, NamedElement {

        private final String name;
        private final String alias;
        @NonNull
        private int id;
    }

    public URI resolveImportUri() {
        return URI.create(getUrl());
    }

    public String getName() {
        if (name != null) {
            return name;
        } else {
            try {
                return UriUtils.getImportNamepsaceFromUri(resolveImportUri());
            } catch (NamespaceException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
