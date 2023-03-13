import java.util.ArrayList;

//Pump Object
public class Pump {

    //    ArrayList to store passenger object relevant to the queue
    private ArrayList<Passenger> pumpQueue =new ArrayList<Passenger>();

    private int queueLength;


    public Pump(int fuelQueueLength) {
        this.queueLength = fuelQueueLength;
    }

    //    Creating Passenger objects and add it to ArrayList
    public void addPassenger(String passengerFirstName, String passengerLastName, String vehicleNumber, int noOfLiters) {
        if (pumpQueue.size()<queueLength){
            pumpQueue.add(new Passenger(passengerFirstName,passengerLastName,vehicleNumber,noOfLiters));
        }else {
            System.out.println("Array is full");
        }

    }

    public int size(){
        return pumpQueue.size();
    }

    public Passenger getPassenger(int passengerIndex){
        return pumpQueue.get(passengerIndex);
    }

    public int getQueueLength() {
        return queueLength;
    }

    public int getNoOfEmpty(){
        return queueLength- pumpQueue.size();
    }

    public void remove(int index){
        pumpQueue.remove(index);
    }

    public void reset(){
        pumpQueue.clear();
    }

    @Override
    public String toString() {
        for (int i = 0; i < pumpQueue.size(); i++) {

            System.out.println(pumpQueue.get(i).toString());
        }
        return null;
    }
}
