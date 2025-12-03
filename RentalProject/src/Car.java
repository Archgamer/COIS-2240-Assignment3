public class Car extends Vehicle implements Rentable {
    private int numSeats;

    public Car(String licensePlate, String make, String model, int year) {
        super(make, model, year);
        setLicensePlate(licensePlate);
        this.numSeats = 4;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        if (numSeats > 0) {
            this.numSeats = numSeats;
        } else {
            System.out.println("Invalid seat number.");
        }
    }

    public void rentVehicle() {
        if (getStatus() == Status.Available) {
            setStatus(Status.Rented);
            System.out.println("Car " + getLicensePlate() + " has been rented.");
        } else {
            System.out.println("Car " + getLicensePlate() + " is not available.");
        }
    }

    public void returnVehicle() {
        if (getStatus() == Status.Rented) {
            setStatus(Status.Available);
            System.out.println("Car " + getLicensePlate() + " has been returned.");
        } else {
            System.out.println("Car " + getLicensePlate() + " was not rented.");
        }
    }

    public String getInfo() {
        return String.format(
            "| %s\t| %s\t| %s\t| %d\t| %s\t| %d seats |",
            getLicensePlate(), getMake(), getModel(), getYear(), getStatus(), numSeats
        );
    }
}
