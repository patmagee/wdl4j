package io.github.patmagee.wdl4j.v1.typing;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class StructType extends Type {

    private String name;

    @Override
    public String getTypeName() {
        return name;
    }
}
