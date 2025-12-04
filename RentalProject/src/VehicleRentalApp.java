import java.util.Scanner;

public class VehicleRentalApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        RentalSystem system = RentalSystem.getInstance();

        while (true) {
            System.out.println("1. Add Vehicle");
            System.out.println("2. Add Customer");
            System.out.println("3. Rent Vehicle");
            System.out.println("4. Return Vehicle");
            System.out.println("5. Display Available Vehicles");
            System.out.println("6. Display Rental History");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            if (!input.hasNextInt()) {
                System.out.println("Invalid choice. Please enter a number.");
                input.nextLine();
                continue;
            }
            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                addVehicle(system, input);
            } else if (choice == 2) {
                addCustomer(system, input);
            } else if (choice == 3) {
                rentVehicle(system, input);
            } else if (choice == 4) {
                returnVehicle(system, input);
            } else if (choice == 5) {
                system.displayAvailableVehicles();
            } else if (choice == 6) {
                system.displayRentalHistory();
            } else if (choice == 7) {
                System.out.println("Bye!");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        input.close();
    }

    private static void addVehicle(RentalSystem system, Scanner input) {
        System.out.println("Select type: 1. Car  2. Minibus  3. PickupTruck");
        System.out.print("Enter type: ");

        if (!input.hasNextInt()) {
            System.out.println("Invalid type. Must be a number.");
            input.nextLine();
            return;
        }
        int type = input.nextInt();
        input.nextLine();

        System.out.print("Enter make: ");
        String make = input.nextLine().trim();

        System.out.print("Enter model: ");
        String model = input.nextLine().trim();

        System.out.print("Enter year: ");
        if (!input.hasNextInt()) {
            System.out.println("Invalid year. Must be a number.");
            input.nextLine();
            return;
        }
        int year = input.nextInt();
        input.nextLine();

        Vehicle vehicle;

        if (type == 1) {
            System.out.print("Enter number of seats: ");
            if (!input.hasNextInt()) {
                System.out.println("Invalid seat number. Must be a number.");
                input.nextLine();
                return;
            }
            int seats = input.nextInt();
            input.nextLine();

            vehicle = new Car("", make, model, year);
            ((Car) vehicle).setNumSeats(seats);

        } else if (type == 2) {
            System.out.print("Accessible (true/false): ");
            if (!input.hasNextBoolean()) {
                System.out.println("Invalid input. Please enter true or false.");
                input.nextLine();
                return;
            }
            boolean accessible = input.nextBoolean();
            input.nextLine();

            vehicle = new Minibus("", make, model, year, accessible);

        } else if (type == 3) {
            System.out.print("Enter cargo size (m3): ");
            if (!input.hasNextDouble()) {
                System.out.println("Invalid cargo size. Must be a number.");
                input.nextLine();
                return;
            }
            double cargoSize = input.nextDouble();

            System.out.print("Has trailer (true/false): ");
            if (!input.hasNextBoolean()) {
                System.out.println("Invalid input. Please enter true or false.");
                input.nextLine();
                return;
            }
            boolean hasTrailer = input.nextBoolean();
            input.nextLine();

            vehicle = new PickupTruck("", make, model, year, cargoSize, hasTrailer);

        } else {
            System.out.println("Invalid vehicle type.");
            return;
        }

        System.out.print("Enter license plate: ");
        String plate = input.nextLine().trim();

        try {
            vehicle.setLicensePlate(plate);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        if (system.addVehicle(vehicle))
            System.out.println("Vehicle added.");
        else
            System.out.println("Add failed (duplicate plate?).");
    }

    private static void addCustomer(RentalSystem system, Scanner input) {
        System.out.print("Enter customer ID: ");
        String id = input.nextLine().trim();

        System.out.print("Enter customer name: ");
        String name = input.nextLine().trim();

        System.out.print("Enter phone number: ");
        String phone = input.nextLine().trim();

        Customer customer = new Customer(id, name, phone);
        if (system.addCustomer(customer)) {
            System.out.println("Customer added.");
        } else {
            System.out.println("Add customer failed (duplicate ID?).");
        }
    }

    private static void rentVehicle(RentalSystem system, Scanner input) {
        System.out.print("Enter plate: ");
        String plate = input.nextLine().trim(); 

        System.out.print("Enter customer ID: ");
        String customerId = input.nextLine().trim();

        if (system.rentVehicle(plate, customerId))
            System.out.println("Rented successfully!");
        else
            System.out.println("Rent failed.");
    }

    private static void returnVehicle(RentalSystem system, Scanner input) {
        System.out.print("Enter plate: ");
        String plate = input.nextLine().trim();

        if (system.returnVehicle(plate))
            System.out.println("Returned successfully.");
        else
            System.out.println("Return failed.");
    }
}
