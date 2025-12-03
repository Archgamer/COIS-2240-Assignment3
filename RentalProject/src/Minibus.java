public class Minibus extends Vehicle implements Rentable {
    private boolean isAccessible;

    public Minibus(String licensePlate, String make, String model, int year, boolean isAccessible) {
        super(make, model, year);
        setLicensePlate(licensePlate);
        this.isAccessible = isAccessible;
    }

    public boolean getAccessible() {
        return isAccessible;
    }

    public void setAccessible(boolean accessible) {
        this.isAccessible = accessible;
    }

    public void rentVehicle() {
        if (getStatus() == Status.Available) {
            setStatus(Status.Rented);
            System.out.println("Minibus " + getLicensePlate() + " has been rented.");
        } else {
            System.out.println("Minibus " + getLicensePlate() + " is not available.");
        }
    }

    public void returnVehicle() {
        if (getStatus() == Status.Rented) {
            setStatus(Status.Available);
            System.out.println("Minibus " + getLicensePlate() + " has been returned.");
        } else {
            System.out.println("Minibus " + getLicensePlate() + " was not rented.");
        }
    }

    public String getInfo() {
        return String.format(
            "| %s\t| %s\t| %s\t| %d\t| %s\t| Accessible: %b |",
            getLicensePlate(), getMake(), getModel(), getYear(), getStatus(), isAccessible
        );
    }
}
