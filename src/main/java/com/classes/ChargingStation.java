package com.classes;

import java.util.Collection;

public interface ChargingStation {

    boolean enqueueVehicle(Vehicle vehicle, int time);

    void chargeVehicles(int time);

    void reset();

    String getName();

    Collection<Vehicle> getWaitingQueue();

    Collection<Vehicle> getChargingQueue();

    boolean allVehiclesCharged();
}
