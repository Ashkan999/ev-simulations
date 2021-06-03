package com;

import com.classes.ChargingStation;
import com.results.ResultsCollection;
import com.results.ResultsWriter;
import com.classes.Vehicle;
import com.scheduling_algorithms.EarliestDeadlineFirst;
import com.scheduling_algorithms.FirstComeFirstServe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.SplittableRandom;

public class Runner {

    public static final int TOTAL_STEPS = 48;
    public static final int MINUTES_PER_STEP = 30;
    public static final int NUMBER_ITERATIONS = 2;
    public static final int STATION_CAPACITY = 1;
    public static final int VEHICLE_MAX_CHARGE = 3;
    public static final int NUMBER_EVS = 5;

    public static final String RESULTS_DIR = "./results";

    public static void main(String[] args) {
        //create distro - depending on distro param
        //run sim loop for a day with number of steps = 24/0.5 (half hour steps)
        //in every step: given the vehicles incoming(distro) and waiting at the CS, schedule them using curr algo

        SplittableRandom random = new SplittableRandom();
        ResultsCollection results = new ResultsCollection();

        LinkedList<ChargingStation> schedulingAlgos = new LinkedList<>();
        schedulingAlgos.add(new FirstComeFirstServe(STATION_CAPACITY));
        schedulingAlgos.add(new EarliestDeadlineFirst(STATION_CAPACITY));


        while(schedulingAlgos.size() > 0) {

            ChargingStation cs = schedulingAlgos.poll();

            int currIteration = 0;

            while(currIteration < NUMBER_ITERATIONS) {

                cs.reset();

                ArrayList<Vehicle> vehicles = new ArrayList<>();
                for(int i = 0; i < NUMBER_EVS; i++) {
                    vehicles.add(new Vehicle(i, VEHICLE_MAX_CHARGE));
                }

                int currStep = 0;

                while(currStep < TOTAL_STEPS) {
                    for(Vehicle vehicle : vehicles) {
                        double randomNumber = random.nextDouble();
                        if(!vehicle.isArrived()) {
                            if(randomNumber <= vehicle.getArrivalProbability(currStep)) { // check bounds
                                //vehicle arrives
                                vehicle.setArrived(true);
                                vehicle.setDeadline(random.nextInt(currStep + 1, TOTAL_STEPS));
                                cs.enqueueVehicle(vehicle, currStep);
                            }
                        }
                    }

                    cs.chargeVehicles(currStep);

                    currStep++;
                }

                results.addWaitingTimeResults(cs.getName(), vehicles, currIteration);

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
//how can we schedule using a prob distro of arrivals - and is it considered online or offline
//how to actually create distros in java (prob not too difficult)
//Scheduling of queueing systems is what i am looking for

//inter arrival time - using poisson


//TODO:
//create actual (poison, normal) distributions (not only for arrivals but also for deadlines bv)
//create lateness performance criterium
//make EDF preemptive
//start implementing actual algos

//make params(#vehicles, ) more advanced
//create optimal algo




//| #Scap | #EVs | ... | day | FCFS_waiting_time | FCFS_max_delay | EDF_waiting_time | EDF_max_delay |
//|                    |  0  |
//|                    |  1  |

//| #days | #EVs | ... | FCFS_waiting_time | FCFS_max_delay | EDF_waiting_time | EDF_max_delay | <--!!