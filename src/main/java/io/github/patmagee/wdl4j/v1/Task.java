package io.github.patmagee.wdl4j.v1;

import io.github.patmagee.wdl4j.v1.api.NamedElement;
import io.github.patmagee.wdl4j.v1.api.NamespaceElement;
import io.github.patmagee.wdl4j.v1.api.WdlElement;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "newBuilder")
public class Task extends AbstractNamespaceElement implements WdlElement, NamedElement, NamespaceElement {

    @NonNull
    private String name;
    private Inputs inputs;
    private List<Declaration> declarations;
    private Command command;
    private Runtime runtime;
    private Outputs outputs;
    private Meta meta;
    private ParameterMeta parameterMeta;

    @Override
    public WdlElement lookupElement(String name) {
        if (inputs != null && inputs.getDeclarations() != null) {
            Optional<Declaration> input = inputs.getDeclarations()
                                                .stream()
                                                .filter(dec -> dec.getName().equals(name))
                                                .findFirst();
            if (input.isPresent()) {
                return input.get();
            }
        } else if (declarations != null) {
            Optional<Declaration> decl = declarations.stream().filter(dec -> dec.getName().equals(name)).findFirst();
            if (decl.isPresent()) {
                return decl.get();
            }
        } else if (outputs != null && outputs.getDeclarations() != null) {
            Optional<Declaration> output = outputs.getDeclarations()
                                                  .stream()
                                                  .filter(dec -> dec.getName().equals(name))
                                                  .findFirst();
            if (output.isPresent()) {
                return output.get();
            }
        }
        return super.lookupElement(name);
    }
}
