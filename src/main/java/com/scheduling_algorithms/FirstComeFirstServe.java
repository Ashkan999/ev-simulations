package com.scheduling_algorithms;

import com.classes.ChargingStation;
import com.classes.Vehicle;

import java.util.Iterator;
import java.util.LinkedList;

public class FirstComeFirstServe implements ChargingStation {

    private int capacity;
    private String name = "FCFS";
    private LinkedList<Vehicle> waitingQueue;
    private LinkedList<Vehicle> chargingQueue;

    public FirstComeFirstServe(int capacity) {
        this.capacity = capacity;
        this.waitingQueue = new LinkedList<>();
        this.chargingQueue = new LinkedList<>();
    }

    public boolean enqueueVehicle(Vehicle vehicle, int time) {
        vehicle.setWaitingTime(time);
        return waitingQueue.add(vehicle);
    }

    public void chargeVehicles(int time) {
        while(waitingQueue.size() > 0 && chargingQueue.size() < capacity) {
            chargingQueue.add(waitingQueue.remove());
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
        this.waitingQueue = new LinkedList<>();
        this.chargingQueue = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public LinkedList<Vehicle> getWaitingQueue() {
        return waitingQueue;
    }

    public LinkedList<Vehicle> getChargingQueue() {
        return chargingQueue;
    }

    @Override
    public boolean allVehiclesCharged() {
        return waitingQueue.size() == 0 && chargingQueue.size() == 0;
    }
}
