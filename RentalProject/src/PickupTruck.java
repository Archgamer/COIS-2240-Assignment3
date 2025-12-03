public class PickupTruck extends Vehicle implements Rentable {

    private double cargoSize;
    private boolean hasTrailer;

    public PickupTruck(String licensePlate, String make, String model, int year,
                       double cargoSize, boolean hasTrailer) {
        super(make, model, year);
    }

    public String getInfo() {
        return "";
    }

    public void rentVehicle() {
    }

    public void returnVehicle() {
    }
}
