package com.scheduling_algorithms;

import com.classes.ChargingStation;
import com.classes.Vehicle;

import java.util.*;

public class ShortestJobFirst extends ChargingStation {

    private LinkedList<Vehicle> chargingQueue;

    public ShortestJobFirst(int capacity) {
        super(capacity, "SJF", new PriorityQueue<>(new SortByChargeTime()));
        this.chargingQueue = new LinkedList<>();
    }

    @Override
    public void chargeVehicles(int time) {
        while(getWaitingQueue().size() > 0 && chargingQueue.size() < getCapacity()) {
            chargingQueue.add(getWaitingQueue().poll());
        }

        Iterator<Vehicle> itr = chargingQueue.iterator();
        while(itr.hasNext()) {
            Vehicle vehicle = itr.next();
            vehicle.charge();
            if(vehicle.isFullyCharged()) {
                vehicle.depart(time);
                itr.remove();
            }
        }
    }

    @Override
    public void reset() {
        setWaitingQueue(new PriorityQueue<>(new SortByChargeTime()));
        chargingQueue = new LinkedList<>();
    }

    @Override
    public PriorityQueue<Vehicle> getWaitingQueue() {
        return (PriorityQueue<Vehicle>) super.getWaitingQueue();
    }

    @Override
    public Collection<Vehicle> getChargingQueue() {
        return chargingQueue;
    }

    @Override
    public boolean allVehiclesCharged() {
        return getWaitingQueue().size() == 0 && chargingQueue.size() == 0;
    }

    static class SortByChargeTime implements Comparator<Vehicle> {

        @Override
        public int compare(Vehicle v1, Vehicle v2) {
            return v1.getMaxCharge() - v1.getStateOfCharge() - (v2.getMaxCharge() - v2.getStateOfCharge());
        }
    }
}
