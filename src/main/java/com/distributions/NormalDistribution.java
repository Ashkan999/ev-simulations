package com.distributions;

public class NormalDistribution extends org.apache.commons.math3.distribution.NormalDistribution implements ProbabilityDistribution {

    private int[] arrivalTimes;

    public NormalDistribution(double mean, double sd) {
        super(mean, sd);
    }

    @Override
    public int[] initArrivalTimes(int numVehicles) {
        arrivalTimes = new int[numVehicles];

        for(int i = 0; i < numVehicles; i++) {
            arrivalTimes[i] = (int) Math.round(this.sample());
        }

        return arrivalTimes;
    }
}
