package com.classes;

import com.Runner;

import java.util.SplittableRandom;

public class Vehicle {
    private int id;
    private double[] distribution;
    private boolean arrived;
    private int stateOfCharge;
    private int maxCharge;
    private int deadline;
    private int waitingTime;

    public Vehicle(int id, int maxCharge) {
        this.id = id;
        this.maxCharge = maxCharge;
        this.arrived = false;
        this.distribution = new double[Runner.TOTAL_STEPS]; // check if vehicles could come at t=0 and t=24
        for(int i = 0; i < distribution.length; i++) {
            distribution[i] = 1.0/distribution.length;
        }
        this.stateOfCharge = new SplittableRandom().nextInt(1, 3);
        this.waitingTime = 0;
    }

    public double getArrivalProbability(int time) {
        double cumulativeProbability = 0.0;
        for(int i = 0; i <= time; i++) {
            cumulativeProbability += distribution[i];
        }
        return cumulativeProbability;
//        return distribution[time];
    }

    public void charge() {
        stateOfCharge++;
    }

    public int getId() {
        return id;
    }

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public boolean isFullyCharged() {
        return stateOfCharge == maxCharge;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", arrived=" + arrived +
                ", stateOfCharge=" + stateOfCharge +
                ", deadline=" + deadline +
                ", waitingTime=" + waitingTime +
                '}';
    }

}
