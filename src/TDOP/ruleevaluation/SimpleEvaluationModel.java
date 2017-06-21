package TDOP.ruleevaluation;

import ec.EvolutionState;
import ec.Fitness;
import ec.util.Parameter;
import TDOP.rule.AbstractRule;
import TDOP.simulation.DynamicSimulation;
import TDOP.simulation.Simulation;
import TDOP.trip.Objective;
import TDOP.trip.SchedulingSet;
import TDOP.trip.TripSetup;

import java.util.ArrayList;
import java.util.List;

/**
 * The simple evaluation model: standard simulation.
 *
 * Created by yimei on 10/11/16.
 */
public class SimpleEvaluationModel extends AbstractEvaluationModel {

    /**
     * The starting seed of the simulation models.
     */
    public final static String P_SIM_SEED = "sim-seed";

    /**
     * Whether to rotate the simulation seed or not.
     */
    public final static String P_ROTATE_SIM_SEED = "rotate-sim-seed";
    public final static String P_SIM_MODELS = "sim-models";
/*
 
    public final static String P_SIM_NUM_MACHINES = "num-machines";
    public final static String P_SIM_NUM_JOBS = "num-jobs";
    public final static String P_SIM_WARMUP_JOBS = "warmup-jobs";
    public final static String P_SIM_MIN_NUM_OPS = "min-num-ops";
    public final static String P_SIM_MAX_NUM_OPS = "max-num-ops";
    public final static String P_SIM_UTIL_LEVEL = "util-level";
    public final static String P_SIM_DUE_DATE_FACTOR = "due-date-factor";
    */
    public final static String P_SIM_REPLICATIONS = "replications";
    
    
    public final static String P_SIM_SPEED_FILE = "speedmatrix";
    public final static String P_SIM_CAT_FILE = "catergory";
    public final static String P_SIM_FILE =  "filename" ;
    public final static String P_SIM_NUM_FILES = "num-files";//or list
    //and and give an int in the params file of number of files.
    //eval model.numfiles = 5;


    protected long simSeed;
    protected boolean rotateSimSeed;

    protected Simulation simulation;

    public long getSimSeed() {
        return simSeed;
    }

    public boolean isRotateSimSeed() {
        return rotateSimSeed;
    }

    @Override
    public void setup(final EvolutionState state, final Parameter base) {
        super.setup(state, base);

        // Get the seed for the simulation.
        Parameter p = base.push(P_SIM_SEED);
        simSeed = state.parameters.getLongWithDefault(p, null, 0);
        //get file name here 
        

        // Get the simulation models.
        p = base.push(P_SIM_MODELS);
        int numSimModels = state.parameters.getIntWithDefault(p, null, 0);

       /* if (numSimModels == 0) {
            System.err.println("ERROR:");
            System.err.println("No simulation model is specified.");
            System.exit(1);
        }*/

       // List<Simulation> trainSimulations = new ArrayList<>();
       // List<Integer> replications = new ArrayList<>();
        //for (int x = 0; x < numSimModels; x++) {
            // Read this simulation model
            Parameter b = base.push(P_SIM_MODELS);
           /* // Number of machines
            p = b.push(P_SIM_NUM_MACHINES);
            int numMachines = state.parameters.getIntWithDefault(p, null, 10);
            // Number of jobs
            p = b.push(P_SIM_NUM_JOBS);
            int numJobs = state.parameters.getIntWithDefault(p, null, 5000);
            // Number of warmup jobs
            p = b.push(P_SIM_WARMUP_JOBS);
            int warmupJobs = state.parameters.getIntWithDefault(p, null, 1000);
            // Min number of operations
            p = b.push(P_SIM_MIN_NUM_OPS);
            int minNumOps = state.parameters.getIntWithDefault(p, null, 2);
            // Max number of operations
            p = b.push(P_SIM_MAX_NUM_OPS);
            int maxNumOps = state.parameters.getIntWithDefault(p, null, numMachines);
            // Utilization level
            p = b.push(P_SIM_UTIL_LEVEL);
            double utilLevel = state.parameters.getDoubleWithDefault(p, null, 0.85);
            // Due date factor
            p = b.push(P_SIM_DUE_DATE_FACTOR);
            double dueDateFactor = state.parameters.getDoubleWithDefault(p, null, 4.0);
            // Number of replications
            p = b.push(P_SIM_REPLICATIONS);
            int rep = state.parameters.getIntWithDefault(p, null, 1);
            	*/
           //Simulation simulation = new DynamicSimulation(simSeed,
            //        null, numMachines, numJobs, warmupJobs,
            //        minNumOps, maxNumOps, utilLevel, dueDateFactor, false);
            p = b.push(P_SIM_SPEED_FILE);
            String speedFile = state.parameters.getString(p, null);
            String catFile = state.parameters.getString(b.push(P_SIM_CAT_FILE), null);
            String dataFile = state.parameters.getString(b.push(P_SIM_FILE), null);
            int rep = state.parameters.getIntWithDefault(b.push(P_SIM_REPLICATIONS), null, 1);
           /*
            * Simulation simulation = new DynamicSimulation(simSeed,
                    null, numMachines, numJobs, warmupJobs,
                    minNumOps, maxNumOps, utilLevel, dueDateFactor, false); 
            */
             simulation = new DynamicSimulation(null, speedFile, catFile, dataFile);
          //  trainSimulations.add(simulation);
          //  replications.add(new Integer(rep));
       // }


        p = base.push(P_ROTATE_SIM_SEED);
        rotateSimSeed = state.parameters.getBoolean(p, null, false);
    }

    @Override
    public void evaluate(Fitness fitness, AbstractRule rule, EvolutionState state) {
        rule.calcFitness(fitness, state, simulation,  objectives);
        //Fitness fitness, EvolutionState state, SchedulingSet schedulingSet, List<Objective> objectives
    }

    @Override
    public boolean isRotatable() {
        return rotateSimSeed;
    }

    @Override
    public void rotate() {
      //  schedulingSet.rotateSeed(objectives);
    }
}
