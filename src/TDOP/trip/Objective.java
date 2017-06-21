package TDOP.trip;

import TDOP.rule.AbstractRule;
import TDOP.rule.basic.NS;


import java.util.HashMap;
import java.util.Map;

/**
 * The enumeration of all the objectives that may be optimised in job shop scheduling.
 * All the objectives are assumed to be minimised.
 *
 * Created by yimei on 28/09/16.
 *
 */
public enum Objective {
	
    TOTAL_NODE_SCORE("total-node-score");

    private final String name;

    Objective(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Reverse-lookup map
    private static final Map<String, Objective> lookup = new HashMap<>();

    static {
        for (Objective a : Objective.values()) {
            lookup.put(a.getName(), a);
        }
    }

    public static Objective get(String name) {
        return lookup.get(name);
    }

    public AbstractRule benchmarkRule() {
        switch (this) {
            case TOTAL_NODE_SCORE:
                return new NS();

        }

        return null;
    }
}
