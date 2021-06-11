package com.classes;

import java.util.Collection;

public abstract class ChargingStation {

    private int capacity;
    private String name;
    private Collection<Vehicle> waitingQueue;

    public ChargingStation(int capacity, String name, Collection<Vehicle> waitingQueue) {
        this.capacity = capacity;
        this.name = name;
        this.waitingQueue = waitingQueue;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Collection<Vehicle> getWaitingQueue() {
        return waitingQueue;
    }

    public void setWaitingQueue(Collection<Vehicle> waitingQueue) {
        this.waitingQueue = waitingQueue;
    }

    public boolean enqueueVehicle(Vehicle vehicle, int time) {
        vehicle.setWaitingTime(time);
        return waitingQueue.add(vehicle);
    }

    public abstract void chargeVehicles(int time);

    public abstract void reset();

    public abstract Collection<Vehicle> getChargingQueue();

    public abstract boolean allVehiclesCharged();
}
