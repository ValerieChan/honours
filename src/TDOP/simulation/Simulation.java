package TDOP.simulation;


import TDOP.trip.Dataset;
import TDOP.trip.Objective;
import TDOP.trip.ProblemData;
import TDOP.rule.AbstractRule;
import TDOP.simulation.event.AbstractEvent;
import TDOP.simulation.event.PlaceVisitedEvent;
import TDOP.simulation.state.SystemState;
import TDOP.trip.POI;
import honours.Problem;
import honours.Vertex;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * The abstract simulation class for evaluating rules.
 *
 * Created by yimei on 21/11/16.
 */
public abstract class Simulation {

    protected AbstractRule rule;
    protected SystemState systemState;
    protected PriorityQueue<AbstractEvent> eventQueue;
    protected ProblemData problem;
    protected List<POI> allPlaces;
	private String speedFile;
	private String categoryFile;
	private String dataFile;

	//protected double maxTime;//double endTime = (test.getTmax()+test.startTime); be careeful...

    public Simulation(AbstractRule rule, String speedFile, String categoryFile, String dataFile) {
        this.rule = rule;
        this.speedFile = speedFile;
        this.categoryFile = categoryFile;
        this.dataFile = dataFile;
        //Problem test = new Problem(speedfile, category, datafile, dset);
        ProblemData problemMade = new ProblemData(new File (speedFile), new File (categoryFile), new File (dataFile), Dataset.VERBEECK);
        this.problem = problemMade;
        systemState = new SystemState(0.0, problem);
        eventQueue = new PriorityQueue<>();
        allPlaces = clone(problem.candiVertices());
    }

    private List<POI> clone(List<POI> candiVertices) {
    	List<POI> clone = new ArrayList<POI>();
    	for(POI p:candiVertices){
    		clone.add(p.copy());
    	}
		return clone;
	}

	public AbstractRule getRule() {
    	//System.out.println("simulation get rule");
        return rule;
    }

    public SystemState getSystemState() {
        return systemState;
    }

    public PriorityQueue<AbstractEvent> getEventQueue() {
        return eventQueue;
    }

    public void setRule(AbstractRule rule) {
        this.rule = rule;
    }

    public double getClockTime() {
        return systemState.getClockTime();
    }

    public void addEvent(AbstractEvent event) {
        eventQueue.add(event);
    }
    
    public void run() {
	//POI end = getSystemStatePOI(systemState.info.endPOI());
    POI current = systemState.info.startPOI();
    visitPOI(current);

    while(systemState.info.getTmax() > systemState.getClockTime() && !systemState.getPlacesUnvisited().isEmpty()){
    	
    	POI next = getRule().placePriority(current, systemState.getPlacesUnvisited(), systemState);
    	double nextTime = systemState.getClockTime() + systemState.info.travelTime(current, next, systemState.getClockTime());
    	
    //	System.out.println(current.id() +" " +totalNodeScore()+" " + systemState.getClockTime() + " "
    //	+nextTime + " "+systemState.info.travelTime(next, systemState.info.endPOI(), nextTime) + " "+ systemState.endTime() +" "+systemState.info.getTmax());
    	
    	if((nextTime  + systemState.info.travelTime(next, systemState.info.endPOI(), nextTime)) 
    			 > systemState.info.getTmax()){

    		 double newTime =systemState.info.travelTime(current, systemState.info.endPOI(), systemState.getClockTime());
    		 systemState.setClockTime(systemState.getClockTime() +newTime);
    		 visitPOI(systemState.info.endPOI());
    		 
    		 //System.out.println("ending without adding any extra:" +totalNodeScore());
    		 break;
			}

    	 double newTime = systemState.info.travelTime(current, next, systemState.getClockTime());
    	 systemState.setClockTime(systemState.getClockTime()+newTime);
    	 visitPOI(next);
		 current = next;

    }
   // System.out.println(systemState.getClockTime());
    //System.out.println("ending "+totalNodeScore());
}

private POI getSystemStatePOI(POI poi) {
	return SystemState.findNode(poi);

	
}

	private List<POI> getPossiblePlaces(List<POI> placesUnvisitedList, POI currentlyAt) {
    	//System.out.println("places unvisited size: "+placesUnvisitedList.size());
    	List<POI> accessible = new ArrayList<POI>();
		for(POI p : placesUnvisitedList){
			if(systemState.info.getArc(currentlyAt, getSystemStatePOI(p)) != null){accessible.add(p.copy());}
		}
		//System.out.println("places unvisited size: "+placesUnvisitedList.size());
		return accessible;
	}

	public void rerun() {
        resetState();
        //System.out.println("re running");
        run();
    }

	public void visitPOI(POI poi) {
		systemState.addPlaceToVisited(poi);
		systemState.removePlaceFromUnvisited(getSystemStatePOI(poi));
	}

    public double totalNodeScore() {
    	//System.out.println("score calculated");
        double value = 0.0;
        for (POI poi : systemState.getPlacesVisited()) {
            value += poi.score(0);     
        }
    //  System.out.println("score calculated"+ va);
        return value;
    }
	
    /**
     * There is only one objective, to maximise the total score of the trip
     * @param objective
     * @return trip score
     */
    public double objectiveValue(Objective objective) {
        switch (objective) {
        case TOTAL_NODE_SCORE:
        	double score = totalNodeScore();
        	if(score  > 100){
        	//System.out.println("simulation-objectiveValue- success- " + score);
        	}
            return score;//(totalNodeScore());
        }
        //System.out.println("simulation-objectiveValue- failure");
        return -1.0;
    }
    public void resetState(){
    	systemState = new SystemState(0.0, new ProblemData(new File (speedFile), new File (categoryFile), new File (dataFile), Dataset.VERBEECK));
    	//systemState.setPlacesUnvisited(clone(allPlaces));//this is still wrong.
    	//systemState.setPlacesVisited(new ArrayList<POI>());
    	//systemState.setClockTime(0.0);
    	//System.out.println("reseting (hopefully) (simulation)"+ systemState.getClockTime()+" "+ systemState.getPlacesUnvisited().size()+" "+systemState.getPlacesVisited().size());;
    }
    
/*

    public abstract void setup();
    
    public abstract void reset();
    public abstract void rotateSeed();

*/

}
