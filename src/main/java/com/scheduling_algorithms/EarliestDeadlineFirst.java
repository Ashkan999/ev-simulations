package com.scheduling_algorithms;

import com.classes.ChargingStation;
import com.classes.Vehicle;

import java.util.*;

public class EarliestDeadlineFirst extends ChargingStation {

//    private int capacity;
//    private String name = "EDF";
//    private PriorityQueue<Vehicle> waitingQueue;
    private LinkedList<Vehicle> chargingQueue;

    public EarliestDeadlineFirst(int capacity) {
        super(capacity, "EDF", new PriorityQueue<>(new SortByDeadline()));
        this.chargingQueue = new LinkedList<>();
//        this.waitingQueue = new PriorityQueue<>(new SortByDeadline());
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
    public boolean allVehiclesCharged() {
        return getWaitingQueue().size() == 0 && chargingQueue.size() == 0;
    }

    @Override
    public void reset() {
        setWaitingQueue(new PriorityQueue<>(new SortByDeadline()));
        this.chargingQueue = new LinkedList<>();
    }

    static class SortByDeadline implements Comparator<Vehicle> {

        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            int compare = o1.getDeadline() - o2.getDeadline();
            if(compare != 0) {
                return compare;
            }
            return o1.getId() - o2.getId(); //TODO: ipv id sort on shortest remaining time bv
        }
    }

}

