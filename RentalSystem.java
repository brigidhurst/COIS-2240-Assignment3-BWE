import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

public class RentalSystem {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();
    
    private static RentalSystem instance;
    
    private RentalSystem() {}
    
    public static RentalSystem getInstance() {
    	if (instance == null)
    		instance = new RentalSystem();
    	return instance;
    }

    public boolean addVehicle(Vehicle vehicle) {
    	if (findVehicleByPlate(vehicle.getLicensePlate()) == null) {
    		vehicles.add(vehicle);
    		saveVehicle(vehicle);
    		return true;
    	}
    	else {
	    	System.out.println("Vehicle with this licence plate already in system.");
	    	return false;
    	}
    }

    public boolean addCustomer(Customer customer) {
    	if (findCustomerById(customer.getCustomerId()) == null) {
    		customers.add(customer);
    		saveCustomer(customer);
    		return true;
    	}
    	else {
	    	System.out.println("Customer with this ID already in system.");
	    	return false;
    	}
    }

    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.Available) {
            vehicle.setStatus(Vehicle.VehicleStatus.Rented);
            rentalHistory.addRecord(new RentalRecord(vehicle, customer, date, amount, "RENT"));
            System.out.println("Vehicle rented to " + customer.getCustomerName());
            saveRecord(new RentalRecord(vehicle, customer, date, amount, "RENT"));
        }
        else {
            System.out.println("Vehicle is not available for renting.");
        }
    }

    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.Rented) {
            vehicle.setStatus(Vehicle.VehicleStatus.Available);
            rentalHistory.addRecord(new RentalRecord(vehicle, customer, date, extraFees, "RETURN"));
            System.out.println("Vehicle returned by " + customer.getCustomerName());
            saveRecord(new RentalRecord(vehicle, customer, date, extraFees, "RETURN"));
        }
        else {
            System.out.println("Vehicle is not rented.");
        }
    }    

    public void displayVehicles(Vehicle.VehicleStatus status) {
        // Display appropriate title based on status
        if (status == null) {
            System.out.println("\n=== All Vehicles ===");
        } else {
            System.out.println("\n=== " + status + " Vehicles ===");
        }
        
        // Header with proper column widths
        System.out.printf("|%-16s | %-12s | %-12s | %-12s | %-6s | %-18s |%n", 
            " Type", "Plate", "Make", "Model", "Year", "Status");
        System.out.println("|--------------------------------------------------------------------------------------------|");
    	  
        boolean found = false;
        for (Vehicle vehicle : vehicles) {
            if (status == null || vehicle.getStatus() == status) {
                found = true;
                String vehicleType;
                if (vehicle instanceof Car) {
                    vehicleType = "Car";
                } else if (vehicle instanceof Minibus) {
                    vehicleType = "Minibus";
                } else if (vehicle instanceof PickupTruck) {
                    vehicleType = "Pickup Truck";
                } else {
                    vehicleType = "Unknown";
                }
                System.out.printf("| %-15s | %-12s | %-12s | %-12s | %-6d | %-18s |%n", 
                    vehicleType, vehicle.getLicensePlate(), vehicle.getMake(), vehicle.getModel(), vehicle.getYear(), vehicle.getStatus().toString());
            }
        }
        if (!found) {
            if (status == null) {
                System.out.println("  No Vehicles found.");
            } else {
                System.out.println("  No vehicles with Status: " + status);
            }
        }
        System.out.println();
    }

    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }
    
    public void displayRentalHistory() {
        if (rentalHistory.getRentalHistory().isEmpty()) {
            System.out.println("  No rental history found.");
        } else {
            // Header with proper column widths
            System.out.printf("|%-10s | %-12s | %-20s | %-12s | %-12s |%n", 
                " Type", "Plate", "Customer", "Date", "Amount");
            System.out.println("|-------------------------------------------------------------------------------|");
            
            for (RentalRecord record : rentalHistory.getRentalHistory()) {                
                System.out.printf("| %-9s | %-12s | %-20s | %-12s | $%-11.2f |%n", 
                    record.getRecordType(), 
                    record.getVehicle().getLicensePlate(),
                    record.getCustomer().getCustomerName(),
                    record.getRecordDate().toString(),
                    record.getTotalAmount()
                );
            }
            System.out.println();
        }
    }
    
    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }
    
    public Customer findCustomerById(int id) {
        for (Customer c : customers)
            if (c.getCustomerId() == id)
                return c;
        return null;
    }
    public void saveVehicle(Vehicle vehicle) {
    	try {
			String vehicleType;
			
			BufferedWriter writer = new BufferedWriter(new FileWriter("vehicles.txt", true));
			BufferedReader reader = new BufferedReader(new FileReader("vehicles.txt"));
            if (vehicle instanceof Car) {
                vehicleType = "Car";
            } else if (vehicle instanceof Minibus) {
                vehicleType = "Minibus";
            } else if (vehicle instanceof PickupTruck) {
                vehicleType = "Pickup Truck";
            } else {
                vehicleType = "Unknown";
                System.out.println("error: unable to get vehicle type when saving vehicle to file: vehicle type dose not exist or is not reconsised");
            }
            if(vehicleType.equals("Unknown")){
            	System.out.println("vehicle skiped");
            }
            else {
            	if(reader.readLine() == null) {
            		writer.write(vehicleType+" "+vehicle.getInfo());
            	}
            	else {
            		writer.write("\n"+vehicleType+" "+vehicle.getInfo());	
            	}
            }
			writer.close();
			reader.close();
            
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public void saveCustomer(Customer customer) {
    	try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt", true));
			BufferedReader reader = new BufferedReader(new FileReader("customers.txt"));
			if(reader.readLine() == null) {
        		writer.write(customer.toString());
        	}
        	else {
        		writer.write("\n"+customer.toString());	
        	}
			writer.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public void saveRecord(RentalRecord record) {
    	try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("rental_records.txt", true));
			BufferedReader reader = new BufferedReader(new FileReader("rental_records.txt"));
			if(reader.readLine() == null) {
        		writer.write(record.toString());
        	}
        	else {
        		writer.write("\n"+record.toString());	
        	}
			writer.close();
			reader.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
    }
}