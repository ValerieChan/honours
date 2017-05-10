package honours;

import java.util.List;

/**
 * The hueristic class creates a method for gennerating a solution.
 * @author ValerieChan
 *
 */
public class Heuristic {

	
	public static void main(String [] args){
		
	}
	
	
	/*
	 * This is a test method that returns the first POI in the list of unvisited.
	 */
	public Vertex returnFirst(Problem P, Vertex current, Double time){
		return P.vertices().get(0);
	}
	
	/*
	 * This method returns the POI which matches the rule:
	 * 		"Nearest neighbour"
	 */
	public Vertex returnNearestEuclidean(Problem P, Vertex current, Double time){
		Vertex nearest = P.vertices().get(0);
		for(Vertex v: P.vertices()){
			System.out.println(current+" "+current.distanceTo(v) +" "+v+"   "+ current.distanceTo(nearest)+" "+ nearest);
			if(current.distanceTo(v) < current.distanceTo(nearest)){ nearest = v;}
		}
		return nearest;
	}
	
	/*
	 * This returns a random POI from the list
	 */
	public Vertex returnRandom(Problem P, Vertex current, Double time){
		int X = (int) (Math.random()/P.vertices().size()*100);
		return P.vertices().get(X);
	}
	
	/*
	 * 
	 */
	public Vertex returnNearestBytravelTime(Problem P, Vertex current, Double time){
		Vertex nearest = P.vertices().get(0);
		for(Vertex v: P.vertices()){
			//System.out.println(current+" "+P.travelTime(current, v, time) +" "+v+"   "+ P.travelTime(current, nearest, time)+" "+ nearest);
			if(P.travelTime(current, v, time) < P.travelTime(current, nearest, time)){ nearest = v;}
		}
		
		//System.out.println(nearest);
		return nearest;
	}

}

