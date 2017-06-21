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

    public CalcPriorityProblem(POI poi,
                               SystemState systemState) {
        this.poi=poi;
        this.systemState = systemState;
    }


    public SystemState getSystemState() {
        return systemState;
    }

    @Override
    public void evaluate(EvolutionState state, Individual ind,
                         int subpopulation, int threadnum) {
    }

	public POI getPlaceOfInterest() {
		return poi;
	}
}
