import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

class VehicleRentalTest {

	@Test
	void testRentAndReturnVehicle() {
		Car aCar = new Car("Toyota", "Corolla", 2020, 5);
		
		Customer aCustomer = new Customer(3, "Brigid");
		assertTrue(aCar.getStatus() == Vehicle.VehicleStatus.Available);
		
		RentalSystem rentalSystem = RentalSystem.getInstance();
		assertTrue(rentalSystem.rentVehicle(aCar, aCustomer, LocalDate.now(), 500));
		assertTrue(aCar.getStatus() == Vehicle.VehicleStatus.Rented);
		assertFalse(rentalSystem.rentVehicle(aCar, aCustomer, LocalDate.now(), 500));
		assertTrue(rentalSystem.returnVehicle(aCar, aCustomer, LocalDate.now(), 0));
		assertTrue(aCar.getStatus() == Vehicle.VehicleStatus.Available);
		assertFalse(rentalSystem.returnVehicle(aCar, aCustomer, LocalDate.now(), 0));
	}
	@Test
	void testLicensePlate() {
		Vehicle car0 = new Car("company", "car", 10, 20);
		Vehicle car1 = new Car("company", "car", 10, 20);
		Vehicle car2 = new Car("company", "car", 10, 20);
		Vehicle bus0 = new Minibus("the_company", "bus", 300000, false);
		Vehicle bus1 = new Minibus("the_company", "bus", 300000, false);
		Vehicle bus2 = new Minibus("the_company", "bus", 300000, false);
		Vehicle truck0 = new PickupTruck("the_company", "AHHHHH", 300000, 1.0, false);
		car0.setLicensePlate("AAA100");
		assertTrue(car0.getLicensePlate().equals("AAA100"));
		car1.setLicensePlate("ABC567");
		assertTrue(car1.getLicensePlate().equals("ABC567"));
		car2.setLicensePlate("ZZZ999");
		assertTrue(car2.getLicensePlate().equals("ZZZ999"));
		
		assertThrows(IllegalArgumentException.class,()->{bus0.setLicensePlate("");});
		assertThrows(IllegalArgumentException.class,()->{bus1.setLicensePlate(null);});
		assertThrows(IllegalArgumentException.class,()->{bus0.setLicensePlate("AAA1000");});
		assertThrows(IllegalArgumentException.class,()->{bus0.setLicensePlate("ZZZ99");});
	}
	
	@Test
	void testSingletonRentalSystem() throws Exception {
		Constructor<RentalSystem> constructor = RentalSystem.class.getDeclaredConstructor();
		assertTrue(constructor.getModifiers() == Modifier.PRIVATE);
		
		assertNotNull(RentalSystem.getInstance());
	}
}
