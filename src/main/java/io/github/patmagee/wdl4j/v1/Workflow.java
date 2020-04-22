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
public class Workflow extends AbstractNamespaceElement implements WdlElement, NamespaceElement, NamedElement {

    @NonNull
    private String name;
    private Inputs inputs;
    private List<WdlElement> elements;
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
        } else if (outputs != null && outputs.getDeclarations() != null) {
            Optional<Declaration> output = outputs.getDeclarations()
                                                  .stream()
                                                  .filter(dec -> dec.getName().equals(name))
                                                  .findFirst();
            if (output.isPresent()) {
                return output.get();
            }
        } else if (elements != null) {

        }

        return super.lookupElement(name);

    }

    private WdlElement lookupInInnerElements(String name, List<WdlElement> elements) {
        if (elements == null) {
            return null;
        }

        for (WdlElement element : elements) {

            if (element instanceof Call && ((Call) element).getName().equals(name)) {
                return getParentNamespace().lookupElement(((Call) element).getTaskName());
            } else if (element instanceof Scatter) {

            } else if (element instanceof Conditional) {

            } else if (element instanceof Declaration && ((Declaration) element).getName().equals(name) ){
                return element;
            }
        }
        return null;

    }
}
