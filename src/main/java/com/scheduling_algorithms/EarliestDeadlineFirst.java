package com.scheduling_algorithms;

import com.classes.ChargingStation;
import com.classes.Vehicle;

import java.util.*;

public class EarliestDeadlineFirst extends ChargingStation {

    private LinkedList<Vehicle> chargingQueue;

    public EarliestDeadlineFirst(int capacity) {
        super(capacity, "EDF", new PriorityQueue<>(new SortByDeadline()));
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
    public PriorityQueue<Vehicle> getWaitingQueue() {
        return (PriorityQueue<Vehicle>) super.getWaitingQueue();
    }

    @Override
    public Collection<Vehicle> getChargingQueue() {
        return chargingQueue;
    }

    @Override
    public boolean vehiclesInQueue() {
        return getWaitingQueue().size() > 0 || chargingQueue.size() > 0;
    }

    @Override
    public void reset() {
        setWaitingQueue(new PriorityQueue<>(new SortByDeadline()));
        this.chargingQueue = new LinkedList<>();
    }

    static class SortByDeadline implements Comparator<Vehicle> {

        @Override
        public int compare(Vehicle v1, Vehicle v2) {
            int compare = v1.getDeadline() - v2.getDeadline();
            if(compare != 0) {
                return compare;
            }
            return v1.getId() - v2.getId(); //TODO: ipv id sort on shortest remaining time bv
        }
    }

}

