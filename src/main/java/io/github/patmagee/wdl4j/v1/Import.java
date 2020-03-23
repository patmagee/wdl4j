package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;
import io.github.patmagee.wdl4j.v1.exception.ExpressionNotAllowedException;
import io.github.patmagee.wdl4j.v1.expression.literal.StringLiteral;

import java.net.URI;
import java.util.List;
import java.util.Objects;

public class Import implements WdlElement {

    private final StringLiteral url;
    private final String name;
    private final List<ImportAlias> aliases;


    public static Builder newBuilder() {
        return new Builder();
    }

    public Import(StringLiteral url, String name, List<ImportAlias> aliases) {
        this.url = url;
        this.name = name;
        this.aliases = aliases;
    }

    private Import(Builder builder) {
        url = builder.url;
        name = builder.name;
        aliases = builder.aliases;
    }

    public StringLiteral getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public List<ImportAlias> getAliases() {
        return aliases;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Import anImport = (Import) o;
        return url.equals(anImport.url) && Objects.equals(name, anImport.name) && Objects.equals(aliases,
                                                                                                 anImport.aliases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, name, aliases);
    }

    public static class ImportAlias implements WdlElement {

        private final String name;
        private final String alias;

        public ImportAlias(String name, String alias) {
            this.name = name;
            this.alias = alias;
        }

        public String getName() {
            return name;
        }

        public String getAlias() {
            return alias;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ImportAlias that = (ImportAlias) o;
            return Objects.equals(name, that.name) && Objects.equals(alias, that.alias);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, alias);
        }
    }

    public static final class Builder {

        private StringLiteral url;
        private String name;
        private List<ImportAlias> aliases;

        private Builder() {
        }

        public Builder withUrl(StringLiteral val) {
            url = val;
            return this;
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withAliases(List<ImportAlias> val) {
            aliases = val;
            return this;
        }

        public Import build() {
            return new Import(this);
        }
    }



}
