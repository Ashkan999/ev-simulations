package com;

import com.classes.ChargingStation;
import com.distributions.NormalDistribution;
import com.distributions.PoissonDistribution;
import com.distributions.ProbabilityDistribution;
import com.distributions.UniformDistribution;
import com.results.ResultsCollection;
import com.results.ResultsWriter;
import com.classes.Vehicle;
import com.scheduling_algorithms.EarliestDeadlineFirst;
import com.scheduling_algorithms.EarliestDeadlineFirstPreemptive;
import com.scheduling_algorithms.FirstComeFirstServe;
import com.scheduling_algorithms.LeastLaxityFirst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Runner { //make static?

    //----START SIMULATION PARAMETERS----

    public static final int MINUTES_PER_STEP = 10;
    public static final int SIMULATION_DURATION = 24; //hours
    public static final int TOTAL_STEPS = SIMULATION_DURATION * 60 / MINUTES_PER_STEP;
    public static final int NUMBER_ITERATIONS = 2000;
    public static final int STATION_CAPACITY = 1;
    public static final int VEHICLE_MAX_CHARGE = 6; //CHARGING_DURATION / MINUTES_PER_STEP
    public static final int NUMBER_EVS = 60;
    public static final boolean VERBOSE = false;

//    public static final ProbabilityDistribution ARRIVALS_DISTRIBUTION = new UniformDistribution(0, TOTAL_STEPS - 1); //bounds?
    public static final ProbabilityDistribution ARRIVALS_DISTRIBUTION = new NormalDistribution(10.0 / SIMULATION_DURATION * TOTAL_STEPS, 3.0);
//    public static final ProbabilityDistribution ARRIVALS_DISTRIBUTION = new PoissonDistribution(1.0);

    public static final UniformDistribution DEADLINES_DISTRIBUTION = new UniformDistribution(1, TOTAL_STEPS); //TODO: fix deadline before arrival time
    //TODO: create normal deadline distro (using papers)

    public static final String RESULTS_DIR = "./results";

    //----END SIMULATION PARAMETERS----

    public static void main(String[] args) {

        ResultsCollection results = new ResultsCollection();

        LinkedList<ChargingStation> schedulingAlgos = new LinkedList<>();
        schedulingAlgos.add(new FirstComeFirstServe(STATION_CAPACITY));
        schedulingAlgos.add(new EarliestDeadlineFirst(STATION_CAPACITY));
        schedulingAlgos.add(new EarliestDeadlineFirstPreemptive(STATION_CAPACITY));
        schedulingAlgos.add(new LeastLaxityFirst(STATION_CAPACITY));


        while(schedulingAlgos.size() > 0) {

            ChargingStation cs = schedulingAlgos.poll();

            int currIteration = 0;

            while(currIteration < NUMBER_ITERATIONS) {

                cs.reset();

                int[] arrivalTimes = ARRIVALS_DISTRIBUTION.initArrivalTimes(NUMBER_EVS);
                int[] deadlines = DEADLINES_DISTRIBUTION.initDeadlines(NUMBER_EVS);

                ArrayList<Vehicle> vehicles = new ArrayList<>();
                for(int i = 0; i < arrivalTimes.length; i++) { //or use num_vehicles and make sure enough arrivalTimes are created
                    vehicles.add(new Vehicle(i, VEHICLE_MAX_CHARGE, arrivalTimes[i], deadlines[i]));
//                    vehicles.add(new Vehicle(i, VEHICLE_MAX_CHARGE, arrivalTimes[i], DEADLINES_DISTRIBUTION));

                    if(VERBOSE) System.out.println("deadline " + i + ": " + deadlines[i]);
                }

                int currStep = 0;

                int countArrivals = 0;
                while(currStep < TOTAL_STEPS || !cs.allVehiclesCharged()) {
                    if(VERBOSE) System.out.println("time " + currStep + ": ");
                    for(Vehicle vehicle : vehicles) {
                        if(!vehicle.isArrived()) {
                            if(vehicle.checkArrives(currStep)) {
                                //vehicle arrives
                                if(VERBOSE) System.out.println("ev " + vehicle.getId() + " arrived");
                                vehicle.setArrived(currStep);
                                if(VERBOSE) System.out.println("WQ before enquing: " + cs.getWaitingQueue().toString());
                                boolean added = cs.enqueueVehicle(vehicle, currStep);
                                if(VERBOSE) System.out.println("enqueued ev " + vehicle.getId() + " " + added);
                                if(VERBOSE) System.out.println("WQ after enquing: " + cs.getWaitingQueue().toString());
                                countArrivals++;
                            }
                        }
                    }

                    if(VERBOSE)System.out.println("WQ before charging: " + cs.getWaitingQueue().toString());
                    cs.chargeVehicles(currStep);
                    if(VERBOSE)System.out.println("WQ after charging: " + cs.getWaitingQueue().toString());

                    currStep++;
                }

                if(VERBOSE) System.out.println("total arrived: " + countArrivals);

                //check how many vehicles not full yet
                int countFulls = 0;
                for(Vehicle v : vehicles) {
                    if (v.isFullyCharged()) {
                        countFulls++;
                    }
                }

                if(VERBOSE) System.out.println(countArrivals + " out of " + vehicles.size() + " arrived");
                if(VERBOSE) System.out.println(countFulls + " out of " + countArrivals + " fully charged");
                if(VERBOSE) System.out.println("waitingQ size " + cs.getWaitingQueue().size());
                if(VERBOSE) System.out.println("waitingQ " + cs.getWaitingQueue().toString());
                if(VERBOSE && cs.getChargingQueue() != null) System.out.println("chargingQ " + cs.getChargingQueue().size());

                assert (countArrivals == vehicles.size());
                assert (countFulls == countArrivals);

                results.addWaitingTimeResults(cs.getName(), vehicles, currIteration);
                results.addTardinessResults(cs.getName(), vehicles, currIteration);

                currIteration++;
            }
        }

        try {
            ResultsWriter.writeToFile(results, RESULTS_DIR, "test");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//Things to figure out:
//Scheduling of queueing systems is what i am looking for

//TODO:
//look deeper into LLF and can laxity be negative?
//check if results are correctly calculated for tardiness
//create actual (poison, normal) distributions (not only for arrivals but also for deadlines bv)
//start implementing actual algos
//should sim stop at end of day or untill all vehicles have been charged? look RP doc for answer
//IDEA if deadlines or other sorting criterium is equal, then charge shortest job first

//make params(#vehicles, ) more advanced
//create optimal algo


//| #Scap | #EVs | ... | day | FCFS_waiting_time | FCFS_max_delay | EDF_waiting_time | EDF_max_delay |
//|                    |  0  |
//|                    |  1  |

//| #days | #EVs | ... | FCFS_waiting_time | FCFS_max_delay | EDF_waiting_time | EDF_max_delay | <--!!