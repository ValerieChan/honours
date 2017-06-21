package TDOP.niching;

import TDOP.rule.AbstractRule;
import TDOP.rule.weighted.WSPT;
import TDOP.simulation.DecisionSituation;
import TDOP.simulation.DynamicSimulation;
import TDOP.trip.POI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * The phenotypic characterisation of rules.
 *
 * Created by YiMei on 3/10/16.
 */

//hypothsis is that i just need a score...
public class PhenoCharacterisation {

    private DecisionSituation decisionSituation;
    private AbstractRule referenceRule;
    private int[] referenceIndexes;

    public PhenoCharacterisation(DecisionSituation decisionSituation,AbstractRule referenceRule) {
        this.decisionSituation = decisionSituation;
        this.referenceRule = referenceRule;
        referenceIndexes = new int[decisionSituation.getQueue().size()];

        calcReferenceIndexes();
    }

    public DecisionSituation getDecisionSituation() {
        return decisionSituation;
    }

    public AbstractRule getReferenceRule() {
        return referenceRule;
    }

    public int[] getReferenceIndexes() {
        return referenceIndexes;
    }

    private void calcReferenceIndexes() {
    	/*
    	ArrayList<POI> unvisited = new ArrayList<>();
    	for(POI p : decisionSituation.getQueue()){
    		unvisited.add(p.copy());
    	}
    	

       for (int i = 0; i < unvisited.size(); i++) {
            //DecisionSituation situation = decisionSituation.getQueue().get(i);
            POI poi = referenceRule.placePriority(unvisited, decisionSituation.getSystemState());
            int index = situation.getQueue().indexOf(poi);
            referenceIndexes[i] = index;
       }*/
    	
    	//need a list of values which say what order the pois are in..rank by 
    	//score and then the place with the biggest score gets no.1???
 
    	
    	
    }
/*
 *     private void calcReferenceIndexes() {
        for (int i = 0; i < decisionSituations.size(); i++) {
            DecisionSituation situation = decisionSituations.get(i);
            Operation op = referenceRule.priorOperation(situation);
            int index = situation.getQueue().indexOf(op);
            referenceIndexes[i] = index;
        }
    }
 */
    public void setReferenceRule(AbstractRule rule) {
        this.referenceRule = rule;

        calcReferenceIndexes();
    }

    public int[] characterise(AbstractRule rule) {

        int[] charList = new int[decisionSituation.getQueue().size()];//want list of POIs
        
        for(int i =0; i< decisionSituation.getQueue().size(); i++){
        	charList[i] = (int) decisionSituation.getQueue().get(i).score(i);
        }
/*
        for (int i = 0; i < decisionSituations.size(); i++) {
            DecisionSituation situation = decisionSituations.get(i);
            List<POI> queue = situation.

            int refIdx = referenceIndexes[i];

            // Calculate the priority for all the operations.
            for (POI poi : queue) {
                poi.setPriority(rule.priority(poi, situation.getSystemState()));
            }

            // get the rank of the processing chosen by the reference rule.
            int rank = 1;
            for (int j = 0; j < queue.size(); j++) {
                if (queue.get(j).priorTo(queue.get(refIdx))) {
                    rank ++;
                }
            }

            charList[i] = rank;
        }*/

        return charList;
    }
/*
    public static PhenoCharacterisation defaultPhenoCharacterisation() {
        AbstractRule refRule = new WSPT();
        int minQueueLength = 10;
        int numDecisionSituations = 20;
        long shuffleSeed = 8295342;

        DynamicSimulation simulation = DynamicSimulation.standardFull(0, refRule,
                10, 500, 0, 0.95, 4.0);

        List<DecisionSituation> situations = simulation.decisionSituations(minQueueLength);
        Collections.shuffle(situations, new Random(shuffleSeed));

        situations = situations.subList(0, numDecisionSituations);
        return new PhenoCharacterisation(situations, refRule);
    }*/

    public static double distance(int[] charList1, int[] charList2) {
        double distance = 0.0;
        for (int i = 0; i < charList1.length; i++) {
            double diff = charList1[i] - charList2[i];
            distance += diff * diff;
        }

        return Math.sqrt(distance);
    }
}
