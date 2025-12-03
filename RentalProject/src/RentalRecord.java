import java.time.LocalDate;

public class RentalRecord {

    private Vehicle vehicle;
    private Customer customer;
    private LocalDate recordDate;
    private double totalAmount;
    private String recordType;

    public RentalRecord(Vehicle vehicle, Customer customer,
                        LocalDate recordDate, double totalAmount,
                        String recordType) {
    }

    public String toString() {
        return "";
    }
}
