package io.github.patmagee.wdl4j.v1.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapLiteral extends Expression {

    private List<MapEntry> values;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MapEntry {
        Expression key;
        Expression value;
    }
}
