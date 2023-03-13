public class Passenger {
    private String passengerFirstName;
    private String passengerLastName;
    private String vehicleNumber;
    private int noOfLiters;

    public Passenger(String passengerFirstName, String passengerLastName, String vehicleNumber, int noOfLiters) {
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.vehicleNumber = vehicleNumber;
        this.noOfLiters = noOfLiters;
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
