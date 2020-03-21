package io.github.patmagee.wdl4j.v1.typing;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PairType extends Type {

    private Type leftType;
    private Type rightType;


    @Override
    public String getTypeName() {
        return "Pair[" + leftType.getTypeName() + "," + rightType.getTypeName() + "]";
    }
}
