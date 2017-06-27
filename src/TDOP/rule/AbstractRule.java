package TDOP.rule;

import ec.EvolutionState;
import ec.Fitness;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleFitness;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import TDOP.trip.Objective;
import TDOP.trip.POI;
import TDOP.trip.SchedulingSet;
import TDOP.simulation.Simulation;
import TDOP.simulation.state.SystemState;

import java.util.List;

/**
 * The abstract dispatching rule for job shop scheduling.
 *
 * Created by yimei on 22/09/16.
 */
public abstract class AbstractRule {

    protected String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public RealMatrix objectiveValueMatrix(SchedulingSet schedulingSet,
                                           List<Objective> objectives) {
        int rows = schedulingSet.getObjectiveLowerBoundMtx().getRowDimension();
        int cols = schedulingSet.getObjectiveLowerBoundMtx().getColumnDimension();

        RealMatrix matrix = new Array2DRowRealMatrix(rows, cols);
        List<Simulation> simulations = schedulingSet.getSimulations();
        int col = 0;

        for (int j = 0; j < simulations.size(); j++) {
            Simulation simulation = simulations.get(j);
            simulation.setRule(this);

            simulation.run();
//            System.out.println(simulation.workCenterUtilLevelsToString());

            for (int i = 0; i < objectives.size(); i++) {
                matrix.setEntry(i, col,
                        simulation.objectiveValue(objectives.get(i)));
            }

            col ++;

            for (int k = 1; k < schedulingSet.getReplications().get(j); k++) {
                simulation.rerun();
//                System.out.println(simulation.workCenterUtilLevelsToString());

                for (int i = 0; i < objectives.size(); i++) {
                    matrix.setEntry(i, col,
                            simulation.objectiveValue(objectives.get(i)));
                }

                col ++;
            }

            simulation.resetState();
        }

        return matrix;
    }

	public void calcFitness(Fitness fitness, EvolutionState state,
			Simulation simulation,
			// SchedulingSet schedulingSet,
			List<Objective> objectives) {

		simulation.setRule(this);
		//simulation.rerun();
		simulation.run();
		
		SimpleFitness f = (SimpleFitness) fitness;
		//KozaFitness f = (KozaFitness) fitness;
		// normalise it with the nearest neighbour score?
		// for now i know its 115 is perfect..
		f.setFitness(state, simulation.objectiveValue(objectives.get(0)) , false);//this is for simple
		//simulation.totalNodeScore()
		
		//f.setStandardizedFitness(state, simulation.totalNodeScore() / 115);
		//f.set
		// MultiObjectiveFitness f = (MultiObjectiveFitness)fitness;
		// f.setObjectives(state, fitness);

		simulation.resetState();
	}
    
    
    public POI placePriority(POI currentlyAt, List<POI> unvisited, SystemState systemState){
    	POI best = unvisited.get(0);
    	best.setPriority(priority(currentlyAt, best, systemState));
    	for(int i =1; i< unvisited.size(); i++){
    		//System.out.println("abstract rule-inside loop "+i);
    		POI current = unvisited.get(i);
    		current.setPriority(priority(currentlyAt, current, systemState));
    		if(current.priorTo(best)){
    			//System.out.println("abstract rule-found a better one");
    			best = current;
    		}
    		
    	}
    	//System.out.println("abstract rule-best score is: "+best.score(0));
    	//return the one with highest priorty
    	return best;
    }
    /*
    public Operation priorOperation(DecisionSituation decisionSituation) {
        List<Operation> queue = decisionSituation.getQueue();
        SystemState systemState = decisionSituation.getSystemState();

        Operation priorOp = queue.get(0);
        priorOp.setPriority(priority(priorOp, workCenter, systemState));

        for (int i = 1; i < queue.size(); i++) {
            Operation op = queue.get(i);
            op.setPriority(priority(op, workCenter, systemState));

            if (op.priorTo(priorOp))
                priorOp = op;
        }

        return priorOp;
    }*/

    public abstract double priority(POI poi, POI current, SystemState systemState);

}
