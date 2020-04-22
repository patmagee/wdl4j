package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "newBuilder")
public class Version implements WdlElement {

    private String release;

}
