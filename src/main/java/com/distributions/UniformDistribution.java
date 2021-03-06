package com.distributions;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.exception.NumberIsTooLargeException;

public class UniformDistribution extends UniformIntegerDistribution implements ProbabilityDistribution {

    private int[] arrivalTimes;
    private int[] deadlines;

    public UniformDistribution(int lower, int upper) throws NumberIsTooLargeException {
        super(lower, upper);
    }

    @Override
    public int[] initArrivalTimes(int numVehicles) {
        arrivalTimes = new int[numVehicles];

        for(int i = 0; i < numVehicles; i++) {
            arrivalTimes[i] = this.sample();
        }

        return arrivalTimes;
    }

    public int[] initDeadlines(int[] arrivalTimes) {
        deadlines = new int[arrivalTimes.length];

        for(int i = 0; i < deadlines.length; i++) {
            UniformIntegerDistribution uni = new UniformIntegerDistribution(arrivalTimes[i], getSupportUpperBound());
            deadlines[i] = uni.sample();
        }

        return deadlines;
    }
}
