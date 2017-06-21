package TDOP.simulation.state;


import TDOP.rule.AbstractRule;
import TDOP.trip.Arc;
import TDOP.trip.POI;
import TDOP.trip.ProblemData;
import TDOP.simulation.event.AbstractEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.mapdb.Fun.Tuple2;

/**
 * The state of the discrete event simulation system.
 *
 * Created by yimei on 22/09/16.
 */
public class SystemState {


	private double clockTime;
    private static List<POI> PlacesUnvisited;
	private List<POI> PlacesVisited;
	private List<POI> allPlaces;
	public static ProblemData info;
	private Map<Tuple2<POI, POI>, Arc> graph;

    public SystemState(double clockTime, ProblemData info) {
        this.clockTime = clockTime;
        this.PlacesUnvisited = info.candiVertices();
        this.PlacesVisited = new ArrayList<>();
        this.allPlaces = info.candiVertices();
        this.info = info;
        //this.graph = info.getCloneOfGraph();
    }
/*
    public SystemState(double clockTime) {
        this(clockTime, new ArrayList<>(), ProblemData info);
    }

    public SystemState() {
        this(0.0);
    }*/

    public List<POI> getAllPlaces() {
		return allPlaces;
	}

    public SystemState(double clockTime2, List<POI> clonedPOIs, ProblemData info2) {
    	this.clockTime = clockTime;
        this.PlacesUnvisited = info.candiVertices();
        this.PlacesVisited = clonedPOIs;
        this.info = info;
	}
	

	public double getClockTime() {
        return clockTime;
    }

    public List<POI> getPlacesUnvisited() {
        return PlacesUnvisited;
    }

    public List<POI> getPlacesVisited() {
        return PlacesVisited;
    }

    public void setClockTime(double clockTime) {
        this.clockTime = clockTime;
    }


    public void setPlacesUnvisited(List<POI> POI_unvisited) {
        this.PlacesUnvisited = POI_unvisited;
    }

    public void setPlacesVisited(List<POI> POI_Visited) {
        this.PlacesVisited = POI_Visited;
    }

    public void addPlaceToSystem(POI poi) {
        PlacesUnvisited.add(poi);
    }

    public void removePlaceFromUnvisited(POI poi) {
    	PlacesUnvisited.remove(poi);
    	/*for(int i=0; i<PlacesUnvisited.size(); i++){
    	POI p = PlacesUnvisited.get(i);
    	int a = p.id();
    	int b = poi.id();
    		if(p.id()==poi.id()){
    			PlacesUnvisited.remove(i); 
    			break;
    			}
    	}*/
    }

    public void addPlaceToVisited(POI poi) {
        PlacesVisited.add(poi);
    }

    public void reset() {
        clockTime = 0.0;
        //PlacesUnvisited= AllPlaces;
        PlacesUnvisited.clear();
        PlacesVisited.clear();
    }


    @Override
    public SystemState clone() {
        List<POI> clonedPOIs = new ArrayList<>();
        for (POI p : PlacesUnvisited) {
            clonedPOIs.add((POI) p.copy());
        }
        System.out.println("cloning");
        return new SystemState(clockTime, clonedPOIs, info);
    }

	public double endTime() {
		return info.getTmax() + info.startTime;
	}

	public static POI findNode(POI poi) {
		for(int i=0; i<info.vertices.size(); i++){
	    	POI p = info.vertices.get(i);
	    	int a = p.id();
	    	int b = poi.id();
	    		if(p.id()==poi.id()){
	    			return info.vertices.get(i); 
	    			}
	    	}
		return null;
	}
}
