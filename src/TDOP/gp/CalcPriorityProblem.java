package TDOP.gp;

import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.simple.SimpleProblemForm;
import TDOP.simulation.state.SystemState;
import TDOP.trip.Operation;
import TDOP.trip.POI;
import TDOP.trip.TouristTrip;

/**
 * Created by YiMei on 27/09/16.
 */
public class CalcPriorityProblem extends Problem implements SimpleProblemForm {

    private POI poi;
    private SystemState systemState;
	private POI current;
	private POI possible;

    public CalcPriorityProblem(POI poi, SystemState systemState) {
        this.poi=poi;
        this.systemState = systemState;
    }


    public CalcPriorityProblem(POI current, POI possible, SystemState systemState) {
    	this.current = current;
    	this.possible = possible;
        this.systemState = systemState;
	}


	public SystemState getSystemState() {
        return systemState;
    }

    @Override
    public void evaluate(EvolutionState state, Individual ind,
                         int subpopulation, int threadnum) {
    }

    public POI getCurrentPOI(){
    	return current;
    }
    public POI getPossiblePOI(){
    	return possible;
    }
	public POI getPlaceOfInterest() {
		return poi;
	}
}
