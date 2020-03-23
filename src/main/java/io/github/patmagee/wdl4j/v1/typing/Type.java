package io.github.patmagee.wdl4j.v1.typing;

import io.github.patmagee.wdl4j.v1.api.WdlElement;

public abstract class Type implements WdlElement {

    public abstract String getTypeName();

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null) {
            return false;
        } else if (o instanceof Type) {
            Type oType = (Type) o;
            return oType.getTypeName().equals(getTypeName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getTypeName().hashCode();
    }

}
