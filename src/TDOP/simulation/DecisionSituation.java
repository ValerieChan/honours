package TDOP.simulation;

import TDOP.trip.Operation;
import TDOP.trip.POI;
import TDOP.simulation.state.SystemState;

import java.util.ArrayList;
import java.util.List;

/**
 * A decision situation.
 *
 * Created by YiMei on 3/10/16.
 */
public class DecisionSituation {

    private List<POI> queue;
    private SystemState systemState;

    public DecisionSituation(List<POI> queue, SystemState systemState) {
        this.queue = queue;
        this.systemState = systemState;
    }

    public List<POI> getQueue() {
        return queue;
    }


    public SystemState getSystemState() {
        return systemState;
    }

    public DecisionSituation clone() {
        List<POI> clonedQ = new ArrayList<>(queue);
        SystemState clonedState = systemState.clone();

        return new DecisionSituation(clonedQ, clonedState);
    }
}
