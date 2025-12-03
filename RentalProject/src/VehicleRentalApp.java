import java.util.Scanner;

public class VehicleRentalApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        RentalSystem system = new RentalSystem();

        while (true) {
            System.out.println("1. Add Vehicle");
            System.out.println("2. Rent Vehicle");
            System.out.println("3. Return Vehicle");
            System.out.println("4. Display Available Vehicles");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                addVehicle(system, input);
            } else if (choice == 2) {
                rentVehicle(system, input);
            } else if (choice == 3) {
                returnVehicle(system, input);
            } else if (choice == 4) {
                system.displayAvailableVehicles();
            } else if (choice == 5) {
                System.out.println("Bye!");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        input.close();
    }

    private static void addVehicle(RentalSystem system, Scanner input) {
        System.out.println("Select type: 1. Car  2. Minibus");
        int type = input.nextInt();
        input.nextLine();

        System.out.print("Enter make: ");
        String make = input.nextLine();
        System.out.print("Enter model: ");
        String model = input.nextLine();
        System.out.print("Enter year: ");
        int year = input.nextInt();
        input.nextLine();

        Vehicle vehicle;
        if (type == 1) {
            System.out.print("Enter number of seats: ");
            int seats = input.nextInt();
            input.nextLine();
            vehicle = new Car("", make, model, year);
            ((Car) vehicle).setNumSeats(seats);
        } else {
            System.out.print("Accessible (true/false): ");
            boolean accessible = input.nextBoolean();
            input.nextLine();
            vehicle = new Minibus("", make, model, year, accessible);
        }

        System.out.print("Enter license plate: ");
        String plate = input.nextLine();
        vehicle.setLicensePlate(plate);

        if (system.addVehicle(vehicle))
            System.out.println("Vehicle added.");
        else
            System.out.println("Add failed.");
    }

    private static void rentVehicle(RentalSystem system, Scanner input) {
        System.out.print("Enter plate: ");
        String plate = input.nextLine();
        System.out.print("Enter customer name: ");
        String name = input.nextLine();

        if (system.rentVehicle(plate, name))
            System.out.println("Rented successfully!");
        else
            System.out.println("Rent failed.");
    }

    private static void returnVehicle(RentalSystem system, Scanner input) {
        System.out.print("Enter plate: ");
        String plate = input.nextLine();

        if (system.returnVehicle(plate))
            System.out.println("Returned successfully.");
        else
            System.out.println("Return failed.");
    }
}
