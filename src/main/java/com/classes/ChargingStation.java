package com.classes;

public interface ChargingStation {

    void enqueueVehicle(Vehicle vehicle, int time);

    void chargeVehicles(int time);

    void reset();

    String getName();

}
