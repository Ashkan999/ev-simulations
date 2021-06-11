package com.scheduling_algorithms;

import com.classes.ChargingStation;
import com.classes.Vehicle;

import java.util.*;

public class EarliestDeadlineFirst implements ChargingStation {

    private int capacity;
    private String name = "EDF";
    private PriorityQueue<Vehicle> waitingQueue;
    private LinkedList<Vehicle> chargingQueue;

    public EarliestDeadlineFirst(int capacity) {
        this.capacity = capacity;
        this.waitingQueue = new PriorityQueue<>(new SortByDeadline());
        this.chargingQueue = new LinkedList<>();
    }

    @Override
    public boolean enqueueVehicle(Vehicle vehicle, int time) {
        vehicle.setWaitingTime(time);
        return waitingQueue.add(vehicle);
    }

    @Override
    public void chargeVehicles(int time) {
        while(waitingQueue.size() > 0 && chargingQueue.size() < capacity) {
            chargingQueue.add(waitingQueue.poll());
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

    public String getName() {
        return name;
    }

    @Override
    public Collection<Vehicle> getWaitingQueue() {
        return waitingQueue;
    }

    @Override
    public Collection<Vehicle> getChargingQueue() {
        return chargingQueue;
    }

    @Override
    public boolean allVehiclesCharged() {
        return waitingQueue.size() == 0 && chargingQueue.size() == 0;
    }

    @Override
    public void reset() {
        this.waitingQueue = new PriorityQueue<>(new SortByDeadline());
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

