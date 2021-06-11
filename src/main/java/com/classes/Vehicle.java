package com.classes;

import org.apache.commons.math3.distribution.IntegerDistribution;

import java.util.SplittableRandom;

public class Vehicle {
    private int id;
//    private IntegerDistribution distribution;
    private boolean arrived;
    private int stateOfCharge;
    private int maxCharge;
    private int deadline;
    private int arrivalTime;
    private int waitingTime;
    private int finishTime;

    public Vehicle(int id, int maxCharge, int arrivalTime, int deadline) {
        this.id = id;
        this.maxCharge = maxCharge;
        this.arrived = false;
        this.arrivalTime = arrivalTime;
        this.stateOfCharge = new SplittableRandom().nextInt(0, maxCharge);
        this.deadline = deadline;
        this.waitingTime = 0;
    }

    public boolean checkArrives(int time) {
        if(time == arrivalTime) return true;
        return false;

//        SplittableRandom random = new SplittableRandom();
//        return random.nextDouble() <= distribution.cumulativeProbability(currStep); // check bounds
    }

    public void depart(int time) {
        setFinishTime(time);
        setWaitingTime(getFinishTime() - getArrivalTime() + 1);
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

    public void setArrived(int time) {
        this.arrived = arrived;
        //init deadline
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

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getStateOfCharge() {
        return stateOfCharge;
    }

    public int getMaxCharge() {
        return maxCharge;
    }

    public int getLaxity(int time) {
        int endTime = time + (maxCharge - stateOfCharge);

        return deadline - endTime; //return Math.max(0, deadline - endTime);??
    }

    /**
     * Returns the tardiness of the vehicle, defined as tardiness = max(0, finishTime - deadline).
     * Note: tardiness (as opposed to lateness) is non-negative.
     *
     * @return tardiness of the vehicle.
     */
    public int getTardiness() {
        return Math.max(0, finishTime - deadline);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (id != vehicle.id) return false;
        if (arrived != vehicle.arrived) return false;
        if (stateOfCharge != vehicle.stateOfCharge) return false;
        if (maxCharge != vehicle.maxCharge) return false;
        if (deadline != vehicle.deadline) return false;
        if (arrivalTime != vehicle.arrivalTime) return false;
        if (waitingTime != vehicle.waitingTime) return false;
        return finishTime == vehicle.finishTime;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (arrived ? 1 : 0);
        result = 31 * result + stateOfCharge;
        result = 31 * result + maxCharge;
        result = 31 * result + deadline;
        result = 31 * result + arrivalTime;
        result = 31 * result + waitingTime;
        result = 31 * result + finishTime;
        return result;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", stateOfCharge=" + stateOfCharge +
                ", deadline=" + deadline +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
}
