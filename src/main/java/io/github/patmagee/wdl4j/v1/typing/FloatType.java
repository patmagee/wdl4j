package io.github.patmagee.wdl4j.v1.typing;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@EqualsAndHashCode
public class FloatType extends Type {

    @Override
    public String getTypeName() {
        return "Float";
    }
}
