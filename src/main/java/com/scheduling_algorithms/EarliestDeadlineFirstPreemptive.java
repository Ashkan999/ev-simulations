package com.scheduling_algorithms;

import com.classes.ChargingStation;
import com.classes.Vehicle;

import java.util.*;

public class EarliestDeadlineFirstPreemptive implements ChargingStation {

    private int capacity;
    private String name = "EDF(pre)";
    private TreeSet<Vehicle> waitingQueue;

    public EarliestDeadlineFirstPreemptive(int capacity) {
        this.capacity = capacity;
        this.waitingQueue = new TreeSet<>(new EarliestDeadlineFirst.SortByDeadline());
    }

    @Override
    public boolean enqueueVehicle(Vehicle vehicle, int time) {
        vehicle.setWaitingTime(time);
        return waitingQueue.add(vehicle);
    }

    @Override
    public void chargeVehicles(int time) {
        Iterator<Vehicle> itr = waitingQueue.iterator();
        int numCharging = 0;
        while(itr.hasNext() && numCharging < capacity) {
            Vehicle vehicle = itr.next();
            vehicle.charge();
            numCharging++;
            if(vehicle.isFullyCharged()) {
                vehicle.depart(time);
                itr.remove();
            }
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public TreeSet<Vehicle> getWaitingQueue() {
        return waitingQueue;
    }

    @Override
    public LinkedList<Vehicle> getChargingQueue() {
        return null;
    }

    @Override
    public boolean allVehiclesCharged() {
        return waitingQueue.size() == 0;
    }

    @Override
    public void reset() {
        this.waitingQueue = new TreeSet<>(new EarliestDeadlineFirst.SortByDeadline());
    }
}
