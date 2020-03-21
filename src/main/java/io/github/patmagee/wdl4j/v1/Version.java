package io.github.patmagee.wdl4j.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import io.github.patmagee.wdl4j.v1.api.WdlElement;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Version implements WdlElement {

    private String release;

}
