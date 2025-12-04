public final class SportCar extends Car {

    private int horsepower;
    private boolean hasTurbo;

    public SportCar(String licensePlate, String make, String model, int year,
                    int horsepower, boolean hasTurbo) {
        super(licensePlate, make, model, year);
        this.horsepower = horsepower;
        this.hasTurbo = hasTurbo;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public boolean hasTurbo() {
        return hasTurbo;
    }

    public String getInfo() {
        return String.format(
            "| %s\t| %s\t| %s\t| %d\t| %s\t| %d HP, Turbo: %b |",
            getLicensePlate(), getMake(), getModel(), getYear(), getStatus(), horsepower, hasTurbo
        );
    }
}
