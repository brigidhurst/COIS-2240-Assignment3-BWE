public abstract class Vehicle {
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private VehicleStatus status;

    public enum VehicleStatus { Available, Held, Rented, UnderMaintenance, OutOfService }

    public Vehicle(String make, String model, int year) {
    	if (make == null || make.isEmpty())
    		this.make = null;
    	else
    		this.make = capitalize(make);
    	
    	if (model == null || model.isEmpty())
    		this.model = null;
    	else
    		this.model = capitalize(model);
    	
        this.year = year;
        this.status = VehicleStatus.Available;
        this.licensePlate = null;
    }

    public Vehicle() {
        this(null, null, 0);
    }

    public void setLicensePlate(String plate) {
    	if(isValidPlate(plate) == true) {
    		this.licensePlate = plate == null ? null : plate.toUpperCase();
    	}
    	else {
    		throw new IllegalArgumentException("Error: Licence plate is empty, does not exist, or is invalid");
    	}
    }

    public void setStatus(VehicleStatus status) {
    	this.status = status;
    }

    public String getLicensePlate() { return licensePlate; }

    public String getMake() { return make; }

    public String getModel() { return model;}

    public int getYear() { return year; }

    public VehicleStatus getStatus() { return status; }

    public String getInfo() {
        return "| " + licensePlate + " | " + make + " | " + model + " | " + year + " | " + status + " |";
    }

    private String capitalize(String input) {
    	return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
    }
    public static boolean isValidPlate(String plate) {
    	if(plate == null || plate.equals("")){
    		
    		return false;
    	}
    	else if(plate.length() == 6) {
    		for(int i = 0; i < plate.length(); i++) {
    			if(i <= 2 && !Character.isLetter(plate.charAt(i))) {
    				
    				return false;
    			}
    			else if(i > 2 && !Character.isDigit(plate.charAt(i))) {
    				
    				return false;
    			}
    		}
    		return true;
    	}
    	else {
    		return false;
    	}
    }
}
