public final class SportCar extends Car {

    private int horsepower;
    private boolean hasTurbo;

    public SportCar(String licensePlate, String make, String model, int year,
                    int horsepower, boolean hasTurbo) {
        super(licensePlate, make, model, year);
    }

    public String getInfo() {
        return "";
    }
}
