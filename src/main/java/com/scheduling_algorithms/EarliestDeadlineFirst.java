package com.scheduling_algorithms;

import com.classes.ChargingStation;
import com.classes.Vehicle;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

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
    public void enqueueVehicle(Vehicle vehicle, int time) {
        vehicle.setWaitingTime(time);
        waitingQueue.add(vehicle);
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
                vehicle.setWaitingTime(time - vehicle.getWaitingTime() + 1);
                itr.remove();
            }
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public void reset() {
        this.waitingQueue = new PriorityQueue<>(new SortByDeadline());
        this.chargingQueue = new LinkedList<>();
    }

    class SortByDeadline implements Comparator<Vehicle> {

        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return o1.getDeadline() - o2.getDeadline();
        }
    }

}

