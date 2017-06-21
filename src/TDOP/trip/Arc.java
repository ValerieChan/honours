package TDOP.trip;

/***
 * An arc (directed edge) in the graph
 * arc category 0: Always busy
 * arc category 1: Morning peak
 * arc category 2: Two peak
 * arc category 3: Evening peak
 * arc category 4: Seldom traveled 
 * 
 * @author yi
 *
 */

public class Arc {

	private POI fromP; // from which vertex
	private POI toP; // to which vertex
	private double length; // Euclidean distance between fromP and toP
	private int category; // the category of the arc
	
	private SpeedTable speedTable; // the speed table of this arc
	
	public Arc(POI fromP, POI toP) {
		this.fromP = fromP;
		this.toP = toP;
		speedTable = new SpeedTable();
	}
	
	public Arc(POI fromP, POI toP, double length, int category, SpeedTable speedTable) {
		this.fromP = fromP;
		this.toP = toP;
		this.length = length;
		this.category = category;
		this.speedTable = speedTable;
	}
	
	public POI fromPOI() {
		return fromP;
	}
	
	public POI toPOI() {
		return toP;
	}
	
	public double length() {
		return length;
	}
	
	public int category() {
		return category;
	}
	
	public SpeedTable speedTable() {
		return speedTable;
	}
	
	public void setLength(double length) {
		this.length = length;
	}
	
	public void setCategory(int category) {
		this.category = category;
	}
	
	public void setSpeedTable(SpeedTable speedTable) {
		this.speedTable = speedTable;
	}
	
	public void printMe() {
		System.out.println("Arc (" + fromP.id() + ", " + toP.id() + "), length = "
				+ length + ", category = " + category);
	}
}
