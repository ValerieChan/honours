package TDOP.gp.terminal;

import org.apache.commons.lang3.math.NumberUtils;

import TDOP.simulation.state.SystemState;
import TDOP.trip.POI;

import java.util.*;

/**
 * The attributes of the Place of interest shop.
 * NOTE: All the attributes are relative to the current time.
 *       This is for making the decision making process memoryless,
 *       i.e. independent of the current time.
 *
 * @author Valerie Chan
 */

public enum PlaceOfInterestAttribute {
    CURRENT_TIME("t"), // the current time absolute
    TRAVEL_TIME_POSSIBLE("tt"), 
    TRAVEL_TIME_POSSIBLE_END("tte"), 
    MAX_TIME("MT"),
    DISTANCE_TO_POSSIBLE("d"), 
    DISTANCE_POSSIBLE_END("de"),
     
    //CURRENT_NODE
    CURRENT_X_CO("cX"),
    CURRENT_Y_CO("cY"),
    NODE_SCORE("NS"),
    
  //other node
    POSS_X_CO("pX"),
    POSS_Y_CO("pY"),

    //end node
    END_X_CO("eX"),
    END_Y_CO("eY"),
    

    
    //RELATIVE_TIME("rt"),//maybe not this one because not time dependent...? 
    //FINISH_TIME("ft")
    ; 

    private final String name;

    PlaceOfInterestAttribute(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Reverse-lookup map
    private static final Map<String, PlaceOfInterestAttribute> lookup = new HashMap<>();

    static {
        for (PlaceOfInterestAttribute a : PlaceOfInterestAttribute.values()) {
            lookup.put(a.getName(), a);
        }
    }

    public static PlaceOfInterestAttribute get(String name) {
        return lookup.get(name);
    }
/*
    public double value(POI placeOfInterest,  SystemState systemState) {
        double value = -1;

        switch (this) {
            case CURRENT_TIME:
                value = systemState.getClockTime();
                break;
            case NODE_SCORE:
                value = placeOfInterest.score(0);//change
                break;
            //case CURRENT_NODE:
            //	value = PlaceOfInterest;
            //	break;
            case X_CO:
            	value = placeOfInterest.xcord();
            	break;
            case Y_CO:
            	value = placeOfInterest.ycord();
            	break;
            default:
                System.err.println("Undefined attribute " + name);
                System.exit(1);
        }

        return value;
    }*/

    
    /**
     * Return the basic attributes.
     * @return the basic attributes.
     */
    public static PlaceOfInterestAttribute[] basicAttributes() {
        return new PlaceOfInterestAttribute[]{
        		PlaceOfInterestAttribute.CURRENT_TIME,
        		PlaceOfInterestAttribute.TRAVEL_TIME_POSSIBLE,
        		PlaceOfInterestAttribute.TRAVEL_TIME_POSSIBLE_END,
        		PlaceOfInterestAttribute.MAX_TIME,
        		PlaceOfInterestAttribute.DISTANCE_TO_POSSIBLE,
        		PlaceOfInterestAttribute.DISTANCE_POSSIBLE_END,
        		
        		PlaceOfInterestAttribute.CURRENT_X_CO,
        		PlaceOfInterestAttribute.CURRENT_Y_CO,
        		PlaceOfInterestAttribute.NODE_SCORE,

        		PlaceOfInterestAttribute.POSS_X_CO,
        		PlaceOfInterestAttribute.POSS_Y_CO,
        		
        		PlaceOfInterestAttribute.END_X_CO,
        		PlaceOfInterestAttribute.END_Y_CO

        };
    }
    
    public static double valueOfString(String attribute,POI current, POI poi,
                                       SystemState systemState,
                                       List<PlaceOfInterestAttribute> ignoredAttributes) {
        PlaceOfInterestAttribute a = get(attribute);
        if (a == null) {
            if (NumberUtils.isNumber(attribute)) {
                return Double.valueOf(attribute);
            } else {
                System.err.println(attribute + " is neither a defined attribute nor a number.");
                System.exit(1);
            }
        }

        if (ignoredAttributes.contains(a)) {
            return 1.0;
        } else {
            return a.value(current, poi, systemState);
        }
    }

	public double value(POI currentPOI, POI possiblePOI, SystemState systemState) {
		double value = -1;

        switch (this) {
            case CURRENT_TIME:
                value = systemState.getClockTime();
                break;
            case MAX_TIME:
            	value = systemState.info.getTmax();
            	break;
            case TRAVEL_TIME_POSSIBLE:
            	value = systemState.info.travelTime(currentPOI, possiblePOI, systemState.getClockTime());
            	break;
            case TRAVEL_TIME_POSSIBLE_END:
            	double nextTime = systemState.getClockTime() + systemState.info.travelTime(currentPOI, possiblePOI, systemState.getClockTime());
            	value = systemState.info.travelTime(possiblePOI, systemState.info.endPOI(), nextTime);
            	break;
            case NODE_SCORE:
                value = possiblePOI.score(0);//change
                break;
            case CURRENT_X_CO:
            	value = currentPOI.xcord();
            	break;
            case CURRENT_Y_CO:
            	value = currentPOI.ycord();
            	break;
            case POSS_X_CO:
            	value = possiblePOI.xcord();
            	break;
            case POSS_Y_CO:
            	value = possiblePOI.ycord();
            	break;
            case END_X_CO:
            	value = systemState.info.endPOI().xcord();
            	break;
            case END_Y_CO:
            	value = systemState.info.endPOI().ycord();
            	break;
            case DISTANCE_TO_POSSIBLE:
            	value = euclideanDistance(currentPOI.xcord(), currentPOI.ycord(), possiblePOI.xcord(), possiblePOI.ycord());
            	break;
            case DISTANCE_POSSIBLE_END:
            	value = euclideanDistance( possiblePOI.xcord(), possiblePOI.ycord(), systemState.info.endPOI().xcord(), systemState.info.endPOI().ycord());
            	break;

            default:
                System.err.println("Undefined attribute " + name);
                System.exit(1);
        }

        return value;
	}

	private double euclideanDistance(double xcord, double ycord, double xcord2,
			double ycord2) {
		return Math.sqrt(Math.pow((xcord - xcord2), 2) + Math.pow((ycord - ycord2), 2));
	}


}
