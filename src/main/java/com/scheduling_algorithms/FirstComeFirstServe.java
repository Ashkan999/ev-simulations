package com.scheduling_algorithms;

import com.classes.ChargingStation;
import com.classes.Vehicle;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

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

    public void enqueueVehicle(Vehicle vehicle, int time) {
        vehicle.setWaitingTime(time);
        waitingQueue.add(vehicle);
    }

    public void chargeVehicles(int time) {
        while(waitingQueue.size() > 0 && chargingQueue.size() < capacity) {
            chargingQueue.add(waitingQueue.remove());
        }

        Iterator<Vehicle> itr = chargingQueue.iterator();
//        System.out.println("time:" + time);
//        System.out.println(chargingQueue);
        while(itr.hasNext()) {
            Vehicle vehicle = itr.next();
            vehicle.charge();
            if(vehicle.isFullyCharged()) {
                vehicle.setWaitingTime(time - vehicle.getWaitingTime() + 1);
                itr.remove();
            }
        }
//        System.out.println(chargingQueue);
    }

    @Override
    public void reset() {
        this.waitingQueue = new LinkedList<>();
        this.chargingQueue = new LinkedList<>();
    }

    public String getName() {
        return name;
    }
}
