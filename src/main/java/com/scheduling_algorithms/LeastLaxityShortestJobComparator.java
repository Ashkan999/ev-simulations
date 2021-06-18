package com.scheduling_algorithms;

import com.classes.Vehicle;

import java.util.Comparator;

public class LeastLaxityShortestJobComparator implements Comparator<Vehicle> {

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
