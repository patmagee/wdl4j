package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;

import java.util.Objects;

public class Version implements WdlElement {

    private String release;

    public static Builder newBuilder() {
        return new Builder();
    }

    public Version(String release) {
        Objects.requireNonNull(release, "The release of a version cannot be null");
        this.release = release;
    }

    private Version(Builder builder) {
        release = builder.release;
    }

    public String getRelease() {
        return release;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Version version = (Version) o;
        return release.equals(version.release);
    }

    @Override
    public int hashCode() {
        return Objects.hash(release);
    }

    public static final class Builder {

        private String release;

        private Builder() {
        }

        public Builder withRelease(String val) {
            release = val;
            return this;
        }

        public Version build() {
            return new Version(this);
        }
    }

}
