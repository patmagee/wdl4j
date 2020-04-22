package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.WdlElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "newBuilder")
public class Outputs implements WdlElement {

    private List<Declaration> declarations;

}
