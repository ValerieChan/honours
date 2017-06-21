package TDOP.rule;

import ec.gp.GPNode;
import ec.gp.GPTree;
import TDOP.feature.ignore.Ignorer;
import TDOP.gp.CalcPriorityProblem;
import TDOP.gp.data.DoubleData;
import TDOP.gp.GPNodeComparator;
import TDOP.rule.AbstractRule;
import TDOP.simulation.state.SystemState;
import TDOP.trip.POI;
import TDOP.util.lisp.LispParser;

/**
 * The GP-evolved rule.
 *
 * Created by YiMei on 27/09/16.
 */
public class GPRule extends AbstractRule {

    private GPTree gpTree;

    public GPRule(GPTree gpTree) {
        name = "\"GPRule\"";
        this.gpTree = gpTree;
    }

    public GPTree getGPTree() {
        return gpTree;
    }

    public void setGPTree(GPTree gpTree) {
        this.gpTree = gpTree;
    }

    public static GPRule readFromLispExpression(String expression) {
        GPTree tree = LispParser.parseJobShopRule(expression);

        return new GPRule(tree);
    }

    public void ignore(GPNode tree, GPNode feature, Ignorer ignorer) {
        if (tree.depth() < feature.depth())
            return;

        if (GPNodeComparator.equals(tree, feature)) {
            ignorer.ignore(tree);

            return;
        }

        if (tree.depth() == feature.depth())
            return;

        for (GPNode child : tree.children) {
            ignore(child, feature, ignorer);
        }
    }

    public void ignore(GPNode feature, Ignorer ignorer) {
        ignore(gpTree.child, feature, ignorer);
    }

    public double priority(POI poi, 
                           SystemState systemState) {
        CalcPriorityProblem calcPrioProb =
                new CalcPriorityProblem(poi, systemState);
        //System.out.println("calculating poi (gp rule) " + calcPrioProb);
        DoubleData tmp = new DoubleData();
        //System.out.println(tmp.value);
        gpTree.child.eval(null, 0, tmp, null, null, calcPrioProb);
        //System.out.println("done");
        return tmp.value;
    }
}
