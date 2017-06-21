package TDOP.simulation;

import org.apache.commons.math3.random.RandomDataGenerator;

import TDOP.simulation.DynamicSimulation;
import TDOP.rule.AbstractRule;
import TDOP.simulation.event.AbstractEvent;
import TDOP.simulation.state.SystemState;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * The dynamic simulation -- discrete event simulation
 *
 * Created by yimei on 22/09/16.
 */
public class DynamicSimulation extends Simulation {

    public final static int SEED_ROTATION = 10000;

    private long seed;
    private RandomDataGenerator randomDataGenerator;
    private static AbstractRule rule;

	private static String speedFile;

	private static String categoryFile;

	private static String dataFile;
   

    public DynamicSimulation(AbstractRule rule, String speedFile, String categoryFile, String dataFile) {
    	
        super(rule, speedFile, categoryFile, dataFile);
        this.rule = rule;
    	this.speedFile = speedFile;//"src/TDOP/speedmatrix.txt";//
    	this.categoryFile = categoryFile;//"src/TDOP/arc_cat_1.txt";//
    	this.dataFile = dataFile;//"src/TDOP/p1.1.a.txt";//

    }

	public static Simulation standardFull(long simSeed, Object object, int i,
			int j, int k, double utilLevel, double dueDateFactor) {
		// TODO Auto-generated method stub
		return new DynamicSimulation(rule, speedFile, categoryFile, dataFile);
	}
   /* public static DynamicSimulation standardFull(
            long seed,
            AbstractRule rule,
            int numWorkCenters,
            int numJobsRecorded,
            int warmupJobs,
            double utilLevel,
            double dueDateFactor) {
        return new DynamicSimulation(seed, rule, numWorkCenters, numJobsRecorded,
                warmupJobs, numWorkCenters, numWorkCenters, utilLevel,
                dueDateFactor, false);
    }
*/
/*
    public List<DecisionSituation> decisionSituations(int minQueueLength) {
        List<DecisionSituation> decisionSituations = new ArrayList<>();

        while (!eventQueue.isEmpty() && throughput < numJobsRecorded) {
            AbstractEvent nextEvent = eventQueue.poll();

            systemState.setClockTime(nextEvent.getTime());
            nextEvent.addDecisionSituation(this, decisionSituations, minQueueLength);
        }

        resetState();

        return decisionSituations;
    }*/

}
