import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

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

}
