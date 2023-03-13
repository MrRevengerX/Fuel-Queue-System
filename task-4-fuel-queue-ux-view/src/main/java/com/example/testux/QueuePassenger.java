package com.example.testux;

public class QueuePassenger {
    private String passengerFirstName;
    private String passengerLastName;
    private String vehicleNumber;
    private int noOfLiters;

    private String queueName;

    public QueuePassenger(String passengerFirstName, String passengerLastName, String vehicleNumber, int noOfLiters, String queueName) {
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.vehicleNumber = vehicleNumber;
        this.noOfLiters = noOfLiters;
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    //    Getters
    public String getPassengerFirstName() {
        return passengerFirstName;
    }

    public String getPassengerLastName() {
        return passengerLastName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public int getNoOfLiters() {
        return noOfLiters;
    }

    @Override
    public String toString() {
        return passengerFirstName + ',' + passengerLastName + ',' + vehicleNumber + ',' + noOfLiters + '\n';
    }
}
