public abstract class Vehicle {

    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private Status status;
    
    public enum Status {
        Available,
        Held,
        Rented,
        UnderMaintenance,
        OutOfService
    }

    public Vehicle(String make, String model, int year) {
        this.make = (make);
        this.model = (model);
        this.year = year;
        this.licensePlate = "";
        this.status = Status.Available;
    }

    public Vehicle() {
        this("Unknown", "Unknown", 0);
    }

    public void setLicensePlate(String plate) {
        this.licensePlate = plate.toUpperCase();
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getInfo() {
        return String.format(
            "| %s\t| %s\t| %s\t| %d\t| %s\t|",
            licensePlate, make, model, year, status
        );
    }
}

