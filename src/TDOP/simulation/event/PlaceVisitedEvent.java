package TDOP.simulation.event;

import yimei.jss.jobshop.*;
import TDOP.trip.Arc;
import TDOP.trip.Operation;
import TDOP.simulation.DynamicSimulation;
import TDOP.simulation.DecisionSituation;
import TDOP.simulation.Simulation;

import java.util.List;

/**
 * Created by yimei on 22/09/16.
 */
public class PlaceVisitedEvent extends AbstractEvent {

    private Arc arc;

    public PlaceVisitedEvent(double time, Arc arc) {
        super(time);
        this.arc = arc;
    }

    public PlaceVisitedEvent( Arc arc) {
        this(arc.getFinishTime(), arc);
    }

    @Override
    public void trigger(Simulation simulation) {
        WorkCenter workCenter = process.getWorkCenter();

        if (!workCenter.getQueue().isEmpty()) {
            DecisionSituation decisionSituation =
                    new DecisionSituation(workCenter.getQueue(), workCenter,
                            simulation.getSystemState());

            Operation dispatchedOp =
                    simulation.getRule().priorOperation(decisionSituation);

            workCenter.removeFromQueue(dispatchedOp);
            Process nextP = new Process(workCenter, process.getMachineId(),
                    dispatchedOp, time);
            simulation.addEvent(new ProcessStartEvent(nextP));
        }

        Operation nextOp = process.getOperation().getNext();

        if (nextOp == null) {
            Job job = process.getOperation().getJob();
            job.setCompletionTime(process.getFinishTime());
            simulation.completeJob(job);
        }
        else {
            simulation.addEvent(new OperationVisitEvent(time, nextOp));
        }
    }

    @Override
    public void addDecisionSituation(DynamicSimulation simulation,
                                     List<DecisionSituation> situations,
                                     int minQueueLength) {
        WorkCenter workCenter = process.getWorkCenter();

        if (!workCenter.getQueue().isEmpty()) {
            DecisionSituation decisionSituation =
                    new DecisionSituation(workCenter.getQueue(), workCenter,
                            simulation.getSystemState());

            if (workCenter.getQueue().size() >= minQueueLength) {
                situations.add(decisionSituation.clone());
            }

            Operation dispatchedOp =
                    simulation.getRule().priorOperation(decisionSituation);

            workCenter.removeFromQueue(dispatchedOp);
            Process nextP = new Process(workCenter, process.getMachineId(),
                    dispatchedOp, time);
            simulation.addEvent(new ProcessStartEvent(nextP));
        }

        Operation nextOp = process.getOperation().getNext();
        if (nextOp == null) {
            Job job = process.getOperation().getJob();
            job.setCompletionTime(process.getFinishTime());
            simulation.completeJob(job);
        }
        else {
            simulation.addEvent(new OperationVisitEvent(time, nextOp));
        }
    }

    @Override
    public String toString() {
        return String.format("%.1f: job %d op %d finished on work center %d.\n",
                time,
                process.getOperation().getJob().getId(),
                process.getOperation().getId(),
                process.getWorkCenter().getId());
    }

    @Override
    public int compareTo(AbstractEvent other) {
        if (time < other.time)
            return -1;

        if (time > other.time)
            return 1;

        if (other instanceof ProcessFinishEvent)
            return 0;

        return 1;
    }

	@Override
	public void addDecisionSituation(
			TDOP.simulation.DynamicSimulation simulation,
			List<TDOP.simulation.DecisionSituation> situations,
			int minQueueLength) {
		// TODO Auto-generated method stub
		
	}
}
