import java.util.ArrayList;

public class RentalHistory {

    private ArrayList<RentalRecord> rentalRecords;

    public RentalHistory() {
        this.rentalRecords = new ArrayList<>();
    }

    public void addRecord(RentalRecord record) {
        if (record != null) {
            rentalRecords.add(record);
        }
    }

    public ArrayList<RentalRecord> getRentalHistory() {
        return rentalRecords;
    }

    public ArrayList<RentalRecord> getRentalRecordsByCustomer(String customerId) {
        ArrayList<RentalRecord> result = new ArrayList<>();
        for (RentalRecord record : rentalRecords) {
            if (record.getCustomer().getCustomerId().equalsIgnoreCase(customerId)) {
                result.add(record);
            }
        }
        return result;
    }

    public ArrayList<RentalRecord> getRentalRecordsByVehicle(String licensePlate) {
        ArrayList<RentalRecord> result = new ArrayList<>();
        for (RentalRecord record : rentalRecords) {
            if (record.getVehicle().getLicensePlate().equalsIgnoreCase(licensePlate)) {
                result.add(record);
            }
        }
        return result;
    }
}
