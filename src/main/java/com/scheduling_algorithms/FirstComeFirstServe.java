package com.scheduling_algorithms;

import com.classes.ChargingStation;
import com.classes.Vehicle;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FirstComeFirstServe extends ChargingStation {

//    private int capacity;
//    private String name = "FCFS";
//    private LinkedList<Vehicle> waitingQueue;
    private LinkedList<Vehicle> chargingQueue;

    public FirstComeFirstServe(int capacity) {
        super(capacity, "FCFS", new LinkedList<>());
        this.chargingQueue = new LinkedList<>();
    }

    public void chargeVehicles(int time) {
        while(getWaitingQueue().size() > 0 && chargingQueue.size() < getCapacity()) {
            chargingQueue.add(getWaitingQueue().remove(0));
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
        setWaitingQueue(new LinkedList<>());
        this.chargingQueue = new LinkedList<>();
    }

    @Override
    public List<Vehicle> getWaitingQueue() {
        return (List<Vehicle>) super.getWaitingQueue();
    }

    public LinkedList<Vehicle> getChargingQueue() {
        return chargingQueue;
    }

    @Override
    public boolean allVehiclesCharged() {
        return getWaitingQueue().size() == 0 && chargingQueue.size() == 0;
    }
}
