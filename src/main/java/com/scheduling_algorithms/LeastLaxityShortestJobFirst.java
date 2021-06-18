package com.scheduling_algorithms;

import com.classes.ChargingStation;
import com.classes.Vehicle;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

//Preemptive
public class LeastLaxityShortestJobFirst extends ChargingStation {

    private LeastLaxityShortestJobComparator LLSJcomparator = new LeastLaxityShortestJobComparator();

    public LeastLaxityShortestJobFirst(int capacity) {
        super(capacity, "LLSJF", null);
        setWaitingQueue(new TreeSet<>(LLSJcomparator));
    }

    @Override
    public void chargeVehicles(int time) {
        LLSJcomparator.setCurrentTime(time);

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
        LLSJcomparator = new LeastLaxityShortestJobComparator();
        setWaitingQueue(new TreeSet<>(LLSJcomparator));
    }

    @Override
    public Collection<Vehicle> getChargingQueue() {
        return null;
    }

    @Override
    public boolean vehiclesInQueue() {
        return getWaitingQueue().size() > 0;
    }

    class LeastLaxityShortestJobComparator implements Comparator<Vehicle> {

        private int currentTime;

        @Override
        public int compare(Vehicle v1, Vehicle v2) {
            int compare = v1.getLaxity(currentTime) - v2.getLaxity(currentTime);
            if(compare != 0) {
                return compare;
            }
            compare = v1.getMaxCharge() - v1.getStateOfCharge() - (v2.getMaxCharge() - v2.getStateOfCharge());
            if(compare != 0) {
                return compare;
            }
            return v1.getId() - v2.getId();
        }

        public void setCurrentTime(int currentTime) {
            this.currentTime = currentTime;
        }
    }
}
