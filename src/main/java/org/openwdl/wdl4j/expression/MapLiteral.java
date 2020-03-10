package org.openwdl.wdl4j.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapLiteral extends Expression {

    private List<MapEntry> values;

    @Override
    public String getElementName() {
        return "Map";
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MapEntry {
        Expression key;
        Expression value;
    }
}
