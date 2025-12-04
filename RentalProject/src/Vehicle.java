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
        if (make == null || make.isEmpty()) {
            this.make = "Unknown";
        } else {
            this.make = capitalize(make);
        }

        if (model == null || model.isEmpty()) {
            this.model = "Unknown";
        } else {
            this.model = capitalize(model);
        }

        this.year = year;
        this.licensePlate = "";
        this.status = Status.Available;
    }

    public Vehicle() {
        this("Unknown", "Unknown", 0);
    }

    private boolean isValidPlate(String plate) {
        if (plate == null) {
            return false;
        }
        String trimmed = plate.trim();
        if (trimmed.isEmpty()) {
            return false;
        }
        String upper = trimmed.toUpperCase();
        return upper.matches("^[A-Z]{3}\\d{3}$");
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

    // Task 5: 提取公共大小写处理逻辑
    private String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String lower = input.toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    public String getInfo() {
        return String.format(
            "| %s\t| %s\t| %s\t| %d\t| %s\t|",
            licensePlate, make, model, year, status
        );
    }
}
