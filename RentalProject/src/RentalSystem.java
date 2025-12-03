import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class RentalSystem {
    private List<Vehicle> vehicles = new ArrayList<>();

    private RentalHistory rentalHistory = new RentalHistory();

    public boolean addVehicle(Vehicle vehicle) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equals(vehicle.getLicensePlate())) {
                System.out.println("Duplicate license plate.");
                return false;
            }
        }
        vehicles.add(vehicle);
        return true;
    }

    public boolean rentVehicle(String licensePlate, String customerName) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equals(licensePlate)) {
                if (v.getStatus() == Vehicle.Status.Available) {
                    v.setStatus(Vehicle.Status.Rented);

                    System.out.println(v.getClass().getSimpleName() + " " + licensePlate
                            + " has been rented by " + formatName(customerName) + ".");
                    return true;
                } else {
                    System.out.println("Vehicle is not available for rent.");
                    return false;
                }
            }
        }
        System.out.println("Vehicle not found.");
        return false;
    }

    public boolean rentVehicle(Vehicle vehicle, Customer customer,
                               LocalDate rentDate, double amount) {

        return false;
    }

    public boolean returnVehicle(String licensePlate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equals(licensePlate)) {
                if (v.getStatus() == Vehicle.Status.Rented) {
                    v.setStatus(Vehicle.Status.Available);


                    System.out.println("Vehicle " + licensePlate + " returned successfully.");
                    return true;
                } else {
                    System.out.println("Vehicle " + licensePlate + " is not currently rented.");
                    return false;
                }
            }
        }
        System.out.println("Vehicle not found.");
        return false;
    }

    public boolean returnVehicle(Vehicle vehicle, Customer customer,
                                 LocalDate returnDate, double extraFees) {

        return false;
    }

    public void displayAvailableVehicles() {
        System.out.println("| Type\t| Plate\t| Make\t| Model\t| Year |");
        System.out.println("--------------------------------------------");

        for (Vehicle v : vehicles) {
            if (v.getStatus() == Vehicle.Status.Available) {
                System.out.printf("| %s\t| %s\t| %s\t| %s\t| %d |\n",
                        v.getClass().getSimpleName().toUpperCase(),
                        v.getLicensePlate(),
                        v.getMake(),
                        v.getModel(),
                        v.getYear());
            }
        }
    }

    private String formatName(String name) {
        if (name.isEmpty()) return name;
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}

