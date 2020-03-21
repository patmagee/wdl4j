package io.github.patmagee.wdl4j.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.ExpressionNotAllowedException;
import io.github.patmagee.wdl4j.v1.expression.StringLiteral;

import java.net.URI;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Import implements WdlElement {

    private StringLiteral url;
    private String name;
    private List<ImportAlias> aliases;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImportAlias implements WdlElement {

        private String name;
        private String alias;
    }

    public URI resolveImportUri() {
        StringBuilder builder = new StringBuilder();
        if (url != null && url.getStringParts() != null) {
            url.getStringParts().forEach(part -> {
                if (part.getExpression() != null) {
                    throw new ExpressionNotAllowedException("Expressions within import statements are not allowed");
                } else {
                    builder.append(part.getStringPart());
                }
            });
        }
        return URI.create(builder.toString());
    }
}
