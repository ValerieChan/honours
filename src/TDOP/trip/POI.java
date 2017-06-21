package TDOP.trip;

import java.util.List;



public class POI {
	private int id;
	private double x;
	private double y;
	private List<Double> scores;
	private double priority;
	


	public POI(int id, double x, double y, List<Double> scores) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.scores = scores;
	}
	
	public int id() {
		return id;
	}
	
	public double xcord() {
		return x;
	}
	
	public double ycord() {
		return y;
	}
	
	public List<Double> scores() {
		return scores;
	}
	
	public double score(int index) {
		return scores.get(index);
	}
	
	public int numScores() {
		return scores.size();
	}
	
	public double distanceTo(POI vertex) {
		double distance = Math.pow(this.x - vertex.xcord(), 2.0) +
				Math.pow(this.y - vertex.ycord(), 2.0);
		distance = Math.sqrt(distance);
		
		return distance;
	}
	
    /**
     * Compare with another process based on priority.
     * @param other the other process.
     * @return true if prior to other, and false otherwise.
     */
    public boolean priorTo(POI other) {
        if (Double.compare(priority, other.priority) < 0)
            return true;

        if (Double.compare(priority, other.priority) > 0)
            return false;

        return true;//if they have the same priority (returns the first one you found)
    }
	
	public double getPriority() {
		return priority;
	}

	public void setPriority(double priority) {
		//System.out.println("setting priority");
		this.priority = priority;
	}
    
	public void printMe() {
		System.out.println("Poi " + id + ": coordinates: (" + x + ", " + y + "), scores: " + scores);
	}

	public POI copy() {
		return new POI(id, x, y, scores);
	}
}
