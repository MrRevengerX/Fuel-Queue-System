package com.example.testux;

import java.util.ArrayList;

//
// Waiting list object
class WaitingList {

    // Declaring the class variables.
    private int size;

    // Declaring array list of for store passenger objects
    private ArrayList<Passenger> waitingQueue = new ArrayList<Passenger>();

    // Constructor
    WaitingList(int size) {
        this.size = size;
    }

    //    Check whether waiting list full
    boolean isFull() {
        if (waitingQueue.size() == size) {
            return true;
        }
        return false;
    }

    //    Check whether waiting list empty
    boolean isEmpty() {
        if (waitingQueue.size() == 0)
            return true;
        else
            return false;
    }

    //    Adding passenger to waiting list
    void enQueue(String passengerFirstName, String passengerLastName, String vehicleNumber, int noOfLiters) {
        waitingQueue.add(new Passenger(passengerFirstName, passengerLastName, vehicleNumber, noOfLiters));
    }

    //    Getting size of waitingQueue
    public int getSize() {
        return waitingQueue.size();
    }

    //    Getting passenger object by index
    public Passenger getPassenger(int index) {
        return waitingQueue.get(index);
    }

    public ArrayList<Passenger> getWaitingQueue() {
        return waitingQueue;
    }

    //    Resetting form
    public void reset() {
        waitingQueue.clear();
    }

    // Remove passenger from waiting list
    public Passenger removeCus() {
        Passenger temp = waitingQueue.get(0);
        waitingQueue.remove(0);
        return temp;
    }

}