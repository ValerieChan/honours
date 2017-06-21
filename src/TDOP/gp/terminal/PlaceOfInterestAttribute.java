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
    //REMAINING_TIME("R"),
    NODE_SCORE("NS"),
    //CURRENT_NODE("CN"), //??
    X_CO("X"),
    Y_CO("Y"),
    //SPEED("s"),//time dependent
    //DISTANCE("d"),
    //TRAVEL_TIME("tt"),
    
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
    }

    
    /**
     * Return the basic attributes.
     * @return the basic attributes.
     */
    public static PlaceOfInterestAttribute[] basicAttributes() {
        return new PlaceOfInterestAttribute[]{
        		PlaceOfInterestAttribute.CURRENT_TIME,
        		//PlaceOfInterestAttribute.REMAINING_TIME,
        		PlaceOfInterestAttribute.NODE_SCORE,
        		//PlaceOfInterestAttribute.CURRENT_NODE, //??
        		PlaceOfInterestAttribute.X_CO,
        		PlaceOfInterestAttribute.Y_CO
        };
    }
    
    public static double valueOfString(String attribute, POI poi,
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
            return a.value(poi, systemState);
        }
    }


}
