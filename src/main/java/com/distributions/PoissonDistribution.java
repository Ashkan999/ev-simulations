package com.distributions;

import com.Runner;

import java.util.SplittableRandom;

public class PoissonDistribution implements ProbabilityDistribution {

    private double lambda;
    private int[] arrivalTimes;

    public PoissonDistribution(double lambda) {
        this.lambda = lambda;
    }

    @Override
    public int[] initArrivalTimes(int numVehicles) {
        arrivalTimes = new int[numVehicles];

        SplittableRandom random = new SplittableRandom();

        double t = 0;
        int i = 0;
        while(i < numVehicles) {
            double interArrivalTime = -1 * Math.log(random.nextDouble()) / lambda;
//            System.out.println("inter " + interArrivalTime);
            t += interArrivalTime;
            if(t > Runner.TOTAL_STEPS) break;
            arrivalTimes[i] = (int) Math.round(t);
            i++;
        }

//        for(int i = 0; i < numVehicles; i++) {
//            double interArrivalTime = -Math.log(random.nextDouble()) / lambda;
//            arrivalTimes[i] = t + (int) Math.round(interArrivalTime);
//            t += interArrivalTime;
//        }

        return arrivalTimes;
    }
}
