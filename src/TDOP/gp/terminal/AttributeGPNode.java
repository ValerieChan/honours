package TDOP.gp.terminal;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import TDOP.gp.CalcPriorityProblem;
import TDOP.gp.data.DoubleData;

/**
 * The PlaceOfInterest attribute as terminal.
 *
 * @author Valerie
 */

public class AttributeGPNode extends GPNode {

    private final PlaceOfInterestAttribute attribute;

    public AttributeGPNode(PlaceOfInterestAttribute attribute) {
        super();
        children = new GPNode[0];
        this.attribute = attribute;
    }

    public PlaceOfInterestAttribute getPlaceOfInterestAttribute() {
        return attribute;
    }

    @Override
    public String toString() {
        return attribute.getName();
    }

    @Override
    public int expectedChildren() {
        return 0;
    }

    @Override
    public void eval(EvolutionState state, int thread, GPData input,
                     ADFStack stack, GPIndividual individual, Problem problem) {
        // The problem is essentially a priority calculation.
        CalcPriorityProblem calcPrioProb = ((CalcPriorityProblem)problem);

        DoubleData data = ((DoubleData)input);
        data.value = attribute.value(
                calcPrioProb.getPlaceOfInterest(),
                calcPrioProb.getSystemState());
    }

    @Override
    public int hashCode() {
        return attribute.getName().hashCode();
    }

    public boolean equals(Object other) {
        if (other instanceof AttributeGPNode) {
            AttributeGPNode o = (AttributeGPNode)other;
            return (attribute == o.attribute);
        }

        return false;
    }
}
