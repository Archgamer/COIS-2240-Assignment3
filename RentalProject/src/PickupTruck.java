public class PickupTruck extends Vehicle implements Rentable {

    private double cargoSize;
    private boolean hasTrailer;

    public PickupTruck(String licensePlate, String make, String model, int year,
                       double cargoSize, boolean hasTrailer) {
        super(make, model, year);
        setLicensePlate(licensePlate);
        this.cargoSize = cargoSize;
        this.hasTrailer = hasTrailer;
    }

    public double getCargoSize() {
        return cargoSize;
    }

    public boolean hasTrailer() {
        return hasTrailer;
    }

    public String getInfo() {
        return String.format(
            "| %s\t| %s\t| %s\t| %d\t| %s\t| Cargo: %.2f m3, Trailer: %b |",
            getLicensePlate(), getMake(), getModel(), getYear(), getStatus(), cargoSize, hasTrailer
        );
    }

    public void rentVehicle() {
        if (getStatus() == Status.Available) {
            setStatus(Status.Rented);
            System.out.println("PickupTruck " + getLicensePlate() + " has been rented.");
        } else {
            System.out.println("PickupTruck " + getLicensePlate() + " is not available.");
        }
    }

    public void returnVehicle() {
        if (getStatus() == Status.Rented) {
            setStatus(Status.Available);
            System.out.println("PickupTruck " + getLicensePlate() + " has been returned.");
        } else {
            System.out.println("PickupTruck " + getLicensePlate() + " was not rented.");
        }
    }
}
