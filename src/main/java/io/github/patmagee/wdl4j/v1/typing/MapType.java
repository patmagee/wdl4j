package io.github.patmagee.wdl4j.v1.typing;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MapType extends Type {

    private Type keyType;
    private Type valueType;


    @Override
    public String getTypeName() {
        return "Map[" + keyType.getTypeName() + "," + valueType.getTypeName() + "]";
    }
}
