import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalSystem {

    private static RentalSystem instance;

    private static final String VEHICLE_FILE = "vehicles.txt";
    private static final String CUSTOMER_FILE = "customers.txt";
    private static final String RECORD_FILE = "rental_records.txt";

    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();

    private RentalSystem() {
        loadData();
    }

    public static synchronized RentalSystem getInstance() {
        if (instance == null) {
            instance = new RentalSystem();
        }
        return instance;
    }

    public boolean addVehicle(Vehicle vehicle) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(vehicle.getLicensePlate())) {
                System.out.println("Duplicate license plate.");
                return false;
            }
        }
        vehicles.add(vehicle);
        saveVehicle(vehicle);
        return true;
    }

    public boolean addCustomer(Customer customer) {
        for (Customer c : customers) {
            if (c.getCustomerId().equalsIgnoreCase(customer.getCustomerId())) {
                System.out.println("Duplicate customer id.");
                return false;
            }
        }
        customers.add(customer);
        saveCustomer(customer); 
        return true;
    }

    public Vehicle findVehicleByPlate(String licensePlate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(licensePlate)) {
                return v;
            }
        }
        return null;
    }

    public Customer findCustomerById(String customerId) {
        for (Customer c : customers) {
            if (c.getCustomerId().equalsIgnoreCase(customerId)) {
                return c;
            }
        }
        return null;
    }

    public boolean rentVehicle(String licensePlate, String customerId) {
        Vehicle v = findVehicleByPlate(licensePlate);
        if (v == null) {
            System.out.println("Vehicle not found.");
            return false;
        }
        if (v.getStatus() != Vehicle.Status.Available) {
            System.out.println("Vehicle is not available for rent.");
            return false;
        }

        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return false;
        }

        v.setStatus(Vehicle.Status.Rented);

        RentalRecord record = new RentalRecord(
                v,
                customer,
                LocalDate.now(),
                0.0,
                "RENT"
        );
        rentalHistory.addRecord(record);
        saveRecord(record);

        System.out.println(v.getClass().getSimpleName() + " " + licensePlate
                + " has been rented by " + customer.getName() + ".");
        return true;
    }

    public boolean rentVehicle(Vehicle vehicle, Customer customer,
                               LocalDate rentDate, double amount) {
        if (vehicle == null || customer == null) {
            return false;
        }
        if (vehicle.getStatus() != Vehicle.Status.Available) {
            return false;
        }
        vehicle.setStatus(Vehicle.Status.Rented);
        RentalRecord record = new RentalRecord(
                vehicle,
                customer,
                rentDate,
                amount,
                "RENT"
        );
        rentalHistory.addRecord(record);
        saveRecord(record);
        return true;
    }

    public boolean returnVehicle(String licensePlate) {
        Vehicle v = findVehicleByPlate(licensePlate);
        if (v == null) {
            System.out.println("Vehicle not found.");
            return false;
        }
        if (v.getStatus() != Vehicle.Status.Rented) {
            System.out.println("Vehicle " + licensePlate + " is not currently rented.");
            return false;
        }
        v.setStatus(Vehicle.Status.Available);

        Customer lastCustomer = null;
        for (int i = rentalHistory.getRentalHistory().size() - 1; i >= 0; i--) {
            RentalRecord r = rentalHistory.getRentalHistory().get(i);
            if (r.getVehicle().getLicensePlate().equalsIgnoreCase(licensePlate)
                    && "RENT".equals(r.getRecordType())) {
                lastCustomer = r.getCustomer();
                break;
            }
        }
        if (lastCustomer == null) {
            lastCustomer = new Customer("N/A", "N/A", "");
        }

        RentalRecord record = new RentalRecord(
                v,
                lastCustomer,
                LocalDate.now(),
                0.0,
                "RETURN"
        );
        rentalHistory.addRecord(record);
        saveRecord(record);

        System.out.println("Vehicle " + licensePlate + " returned successfully.");
        return true;
    }

    public void displayAvailableVehicles() {
        System.out.println("Available Vehicles:");
        System.out.println("| Type\t| Plate\t| Make\t| Model\t| Year |");
        for (Vehicle v : vehicles) {
            if (v.getStatus() == Vehicle.Status.Available) {
                System.out.printf("| %s\t| %s\t| %s\t| %s\t| %d |%n",
                        v.getClass().getSimpleName().toUpperCase(),
                        v.getLicensePlate(),
                        v.getMake(),
                        v.getModel(),
                        v.getYear());
            }
        }
    }

    public void displayRentalHistory() {
        for (RentalRecord record : rentalHistory.getRentalHistory()) {
            System.out.println(record);
        }
    }

    public void saveVehicle(Vehicle vehicle) {
        try (PrintWriter out = new PrintWriter(new FileWriter(VEHICLE_FILE, true))) {
            out.printf("%s,%s,%s,%s,%d%n",
                    vehicle.getClass().getSimpleName(),
                    vehicle.getLicensePlate(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getYear());
        } catch (IOException e) {
            System.err.println("Error saving vehicle: " + e.getMessage());
        }
    }

    public void saveCustomer(Customer customer) {
        try (PrintWriter out = new PrintWriter(new FileWriter(CUSTOMER_FILE, true))) {
            out.printf("%s,%s,%s%n",
                    customer.getCustomerId(),
                    customer.getName(),
                    customer.getPhoneNumber());
        } catch (IOException e) {
            System.err.println("Error saving customer: " + e.getMessage());
        }
    }

    public void saveRecord(RentalRecord record) {
        try (PrintWriter out = new PrintWriter(new FileWriter(RECORD_FILE, true))) {
            out.printf("%s,%s,%s,%s,%.2f%n",
                    record.getRecordType(),
                    record.getRecordDate(),
                    record.getVehicle().getLicensePlate(),
                    record.getCustomer().getCustomerId(),
                    record.getTotalAmount());
        } catch (IOException e) {
            System.err.println("Error saving record: " + e.getMessage());
        }
    }

    private void loadData() {
        loadVehicles();
        loadCustomers();
        loadRentalRecords();
    }

    private void loadVehicles() {
        File file = new File(VEHICLE_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) {
                    continue;
                }
                String type = parts[0];
                String plate = parts[1];
                String make = parts[2];
                String model = parts[3];
                int year = Integer.parseInt(parts[4]);

                Vehicle vehicle;
                if (type.equalsIgnoreCase("Car")) {
                    vehicle = new Car(plate, make, model, year);
                } else if (type.equalsIgnoreCase("Minibus")) {
                    vehicle = new Minibus(plate, make, model, year, false);
                } else if (type.equalsIgnoreCase("PickupTruck")) {
                    vehicle = new PickupTruck(plate, make, model, year, 0.0, false);
                } else if (type.equalsIgnoreCase("SportCar")) {
                    vehicle = new SportCar(plate, make, model, year, 0, false);
                } else {
                    vehicle = new Car(plate, make, model, year);
                }
                vehicles.add(vehicle);
            }
        } catch (IOException e) {
            System.err.println("Error loading vehicles: " + e.getMessage());
        }
    }

    private void loadCustomers() {
        File file = new File(CUSTOMER_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) {
                    continue;
                }
                String id = parts[0];
                String name = parts[1];
                String phone = parts[2];
                customers.add(new Customer(id, name, phone));
            }
        } catch (IOException e) {
            System.err.println("Error loading customers: " + e.getMessage());
        }
    }

    private void loadRentalRecords() {
        File file = new File(RECORD_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) {
                    continue;
                }
                String type = parts[0];
                LocalDate date = LocalDate.parse(parts[1]);
                String plate = parts[2];
                String customerId = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Vehicle v = findVehicleByPlate(plate);
                Customer c = findCustomerById(customerId);
                if (v != null && c != null) {
                    RentalRecord record = new RentalRecord(v, c, date, amount, type);
                    rentalHistory.addRecord(record);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading rental records: " + e.getMessage());
        }
    }
}
