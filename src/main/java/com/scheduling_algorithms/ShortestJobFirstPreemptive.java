package com.scheduling_algorithms;

import com.classes.ChargingStation;
import com.classes.Vehicle;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

public class ShortestJobFirstPreemptive extends ChargingStation {

    public ShortestJobFirstPreemptive(int capacity) {
        super(capacity, "SJF(pre)", new TreeSet<>(new ShortestJobFirst.SortByChargeTime()));
    }

    @Override
    public void chargeVehicles(int time) {
        Iterator<Vehicle> itr = getWaitingQueue().iterator();
        int numCharging = 0;
        while(itr.hasNext() && numCharging < getCapacity()) {
            Vehicle vehicle = itr.next();
            vehicle.charge();
            numCharging++;
            if(vehicle.isFullyCharged()) {
                vehicle.depart(time);
                itr.remove();
            }
        }
    }

    @Override
    public void reset() {
        setWaitingQueue(new TreeSet<>(new ShortestJobFirst.SortByChargeTime()));
    }

    @Override
    public Collection<Vehicle> getChargingQueue() {
        return null;
    }

    @Override
    public boolean vehiclesInQueue() {
        return getWaitingQueue().size() > 0;
    }
}
