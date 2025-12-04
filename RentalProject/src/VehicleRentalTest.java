import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleRentalTest {

    private RentalSystem system;

    @BeforeEach
    void setUp() {
        system = RentalSystem.getInstance();
    }

    @Test
    void testLicensePlate() {
        Vehicle v1 = new Car("AAA100", "Toyota", "Corolla", 2019);
        assertEquals("AAA100", v1.getLicensePlate());

        Vehicle v2 = new Car("ABC567", "Honda", "Civic", 2020);
        assertEquals("ABC567", v2.getLicensePlate());

        Vehicle v3 = new Car("ZZZ999", "Ford", "Focus", 2021);
        assertEquals("ZZZ999", v3.getLicensePlate());

        Vehicle base = new Car("AAA100", "Toyota", "Corolla", 2019);

        assertThrows(IllegalArgumentException.class,
                () -> base.setLicensePlate(""));
        assertThrows(IllegalArgumentException.class,
                () -> base.setLicensePlate(null));
        assertThrows(IllegalArgumentException.class,
                () -> base.setLicensePlate("AAA1000"));
        assertThrows(IllegalArgumentException.class,
                () -> base.setLicensePlate("ZZZ99"));

        assertFalse("AAA1000".matches("^[A-Z]{3}\\d{3}$"));
        assertFalse("ZZZ99".matches("^[A-Z]{3}\\d{3}$"));
    }

    @Test
    void testRentAndReturnVehicle() {
        Vehicle car = new Car("TES123", "Tesla", "Model3", 2022);
        Customer customer = new Customer("C001", "George", "555-1234");

        system.addVehicle(car);
        system.addCustomer(customer);

        assertEquals(Vehicle.Status.Available, car.getStatus());

        boolean rentSuccess = system.rentVehicle("TES123", "C001");
        assertTrue(rentSuccess);
        assertEquals(Vehicle.Status.Rented, car.getStatus());

        boolean rentAgain = system.rentVehicle("TES123", "C001");
        assertFalse(rentAgain);
        assertEquals(Vehicle.Status.Rented, car.getStatus());

        boolean returnSuccess = system.returnVehicle("TES123");
        assertTrue(returnSuccess);
        assertEquals(Vehicle.Status.Available, car.getStatus());

        boolean returnAgain = system.returnVehicle("TES123");
        assertFalse(returnAgain);
        assertEquals(Vehicle.Status.Available, car.getStatus());
    }

    @Test
    void testSingletonRentalSystem() throws Exception {
        Constructor<RentalSystem> constructor =
                RentalSystem.class.getDeclaredConstructor();

        int modifiers = constructor.getModifiers();

        assertEquals(Modifier.PRIVATE, modifiers);

        RentalSystem rs1 = RentalSystem.getInstance();
        RentalSystem rs2 = RentalSystem.getInstance();

        assertNotNull(rs1);
        assertSame(rs1, rs2);
    }
}
