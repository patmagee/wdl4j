package org.openwdl.wdl4j.v1.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URI;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileLiteral extends Expression {

    private URI value;

    @Override
    public String getElementName() {
        return "File";
    }
}
