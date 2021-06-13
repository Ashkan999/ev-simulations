package com.scheduling_algorithms;

import com.classes.ChargingStation;
import com.classes.Vehicle;

import java.util.*;

public class EarliestDeadlineFirstPreemptive extends ChargingStation {

    public EarliestDeadlineFirstPreemptive(int capacity) {
        super(capacity, "EDF(pre)", new TreeSet<>(new EarliestDeadlineFirst.SortByDeadline()));
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
    public LinkedList<Vehicle> getChargingQueue() {
        return null;
    }

    @Override
    public boolean allVehiclesCharged() {
        return getWaitingQueue().size() == 0;
    }

    @Override
    public void reset() {
        setWaitingQueue(new TreeSet<>(new EarliestDeadlineFirst.SortByDeadline()));
    }
}
