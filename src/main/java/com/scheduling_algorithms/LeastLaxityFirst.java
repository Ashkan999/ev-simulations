package com.scheduling_algorithms;

import com.classes.ChargingStation;
import com.classes.Vehicle;

import java.util.*;

public class LeastLaxityFirst extends ChargingStation {

    private SortByLaxity sortByLaxity = new SortByLaxity();

    public LeastLaxityFirst(int capacity) {
        super(capacity, "LLF", null);
        setWaitingQueue(new TreeSet<>(sortByLaxity));
    }

    @Override
    public boolean enqueueVehicle(Vehicle vehicle, int time) {
        vehicle.setWaitingTime(time);
        return getWaitingQueue().add(vehicle);
    }

    @Override
    public void chargeVehicles(int time) {
        sortByLaxity.setCurrentTime(time);

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
        sortByLaxity = new SortByLaxity();
        setWaitingQueue(new TreeSet<>(sortByLaxity));
    }

    @Override
    public Collection<Vehicle> getChargingQueue() {
        return null;
    }

    @Override
    public boolean allVehiclesCharged() {
        return getWaitingQueue().size() == 0;
    }

    class SortByLaxity implements Comparator<Vehicle> {

        private int currentTime;

        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            int compare = o1.getLaxity(currentTime) - o2.getLaxity(currentTime);
            if(compare != 0) {
                return compare;
            }
            return o1.getId() - o2.getId(); //TODO: ipv on id sort on shortest remaining time bv
        }

        public void setCurrentTime(int currentTime) {
            this.currentTime = currentTime;
        }
    }
}
