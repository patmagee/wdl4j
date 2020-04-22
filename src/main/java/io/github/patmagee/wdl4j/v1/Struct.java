package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.NamedElement;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "newBuilder")
public class Struct implements WdlElement, NamedElement {

    @NonNull
    private String name;
    @NonNull
    private List<Declaration> members;

}
