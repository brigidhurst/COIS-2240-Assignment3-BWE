import java.time.LocalDate;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RentalSystemGUI extends Application {
	static RentalSystem rentalSystem = RentalSystem.getInstance();
    @Override
    public void start(Stage primaryStage) {
    	
    	ScrollPane rentalHistoryPane = new ScrollPane(new TextArea(getRentalLogs()));
    	ScrollPane availableVehiclesPane = new ScrollPane(new TextArea(getVehicleLogs(Vehicle.VehicleStatus.Available)));
        ScrollPane rentedVehiclesPane = new ScrollPane(new TextArea(getVehicleLogs(Vehicle.VehicleStatus.Rented)));

        // Create TabPane
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // ===== ADD TAB =====
        Tab addTab = new Tab("Add");

        GridPane addTabGrid = new GridPane();
        addTabGrid.setHgap(10);
        addTabGrid.setVgap(10);
        addTabGrid.setPadding(new Insets(20));

        
        Label addVehicleLabel = new Label("Add Vehicle");
        Label vehicleTypeLabel = new Label("Vehicle Type:");
        Label vehiclePlateLabel = new Label("License Plate:");
        Label vehicleMakeLabel = new Label("Make:");
        Label vehicleModelLabel = new Label("Model:");
        Label vehicleYearLabel = new Label("Year:");
        
        Label vehicleNumSeatsLabel = new Label("Number of Seats:");
        Label vehicleCargoSizeLabel = new Label("Cargo Size:");
        StackPane fillableLabelStack = new StackPane(vehicleNumSeatsLabel, vehicleCargoSizeLabel);
        
        Label vehicleHorsepowerLabel = new Label("Horsepower:");
        
        Label vehicleIsAccessibleLabel = new Label("Is Accessible:");
        Label vehicleHasTrailerLabel = new Label("Has Trailer:");
        Label vehicleHasTurboLabel = new Label("Has Turbo:");
        StackPane booleanLabelStack = new StackPane(vehicleIsAccessibleLabel, vehicleHasTrailerLabel, vehicleHasTurboLabel);
        
        Label vehicleAddMessageLabel = new Label();

        
        ComboBox<String> vehicleTypeDropdown = new ComboBox<>();
        vehicleTypeDropdown.getItems().addAll("Choose a Value", "Car", "Minibus", "Pickup Truck", "Sport Car");
        vehicleTypeDropdown.setValue("Choose a Value");

        TextField vehiclePlateField = new TextField();
        TextField vehicleMakeField = new TextField();
        TextField vehicleModelField = new TextField();
        TextField vehicleYearField = new TextField();
        
        TextField fillableField = new TextField();
        
        TextField vehicleHorsepowerField = new TextField();
        
        CheckBox booleanBox = new CheckBox();
        
        Button addVehicleButton = new Button("Add Vehicle");
        
        vehicleNumSeatsLabel.visibleProperty().bind(
    	    Bindings.or(
    	    		vehicleTypeDropdown.valueProperty().isEqualTo("Car"),
    	    		vehicleTypeDropdown.valueProperty().isEqualTo("Sport Car")
    	    )
        );
        
        vehicleCargoSizeLabel.visibleProperty().bind(
    		vehicleTypeDropdown.valueProperty().isEqualTo("Pickup Truck")
        );
        
        fillableField.visibleProperty().bind(
    		Bindings.and(
    	    		vehicleTypeDropdown.valueProperty().isNotEqualTo("Choose a Value"),
    	    		vehicleTypeDropdown.valueProperty().isNotEqualTo("Minibus")
    	    )
        );
        
        vehicleHorsepowerLabel.visibleProperty().bind(
        		vehicleTypeDropdown.valueProperty().isEqualTo("Sport Car")
        );
        vehicleHorsepowerField.visibleProperty().bind(
        		vehicleTypeDropdown.valueProperty().isEqualTo("Sport Car")
        );
        
        vehicleIsAccessibleLabel.visibleProperty().bind(
        		vehicleTypeDropdown.valueProperty().isEqualTo("Minibus")
        );
        vehicleHasTrailerLabel.visibleProperty().bind(
        		vehicleTypeDropdown.valueProperty().isEqualTo("Pickup Truck")
        );
        vehicleHasTurboLabel.visibleProperty().bind(
        		vehicleTypeDropdown.valueProperty().isEqualTo("Sport Car")
        );
        
        booleanBox.visibleProperty().bind(
        		Bindings.and(
        	    		vehicleTypeDropdown.valueProperty().isNotEqualTo("Choose a Value"),
        	    		vehicleTypeDropdown.valueProperty().isNotEqualTo("Car")
        	    )
            );
        
        addTabGrid.add(addVehicleLabel, 1, 0);

        addTabGrid.add(vehicleTypeLabel, 0, 1);
        addTabGrid.add(vehicleTypeDropdown, 1, 1);

        addTabGrid.add(vehiclePlateLabel, 0, 2);
        addTabGrid.add(vehiclePlateField, 1, 2);

        addTabGrid.add(vehicleMakeLabel, 0, 3);
        addTabGrid.add(vehicleMakeField, 1, 3);

        addTabGrid.add(vehicleModelLabel, 0, 4);
        addTabGrid.add(vehicleModelField, 1, 4);

        addTabGrid.add(vehicleYearLabel, 0, 5);
        addTabGrid.add(vehicleYearField, 1, 5);
        
        addTabGrid.add(fillableLabelStack, 0, 6);
        addTabGrid.add(fillableField, 1, 6);
        
        addTabGrid.add(vehicleHorsepowerLabel, 0, 7);
        addTabGrid.add(vehicleHorsepowerField, 1, 7);
        
        addTabGrid.add(booleanLabelStack, 0, 8);
        addTabGrid.add(booleanBox, 1, 8);
        
        addTabGrid.add(addVehicleButton, 1, 9);
        addTabGrid.add(vehicleAddMessageLabel, 1, 10);
        
        
        Label addCustomerLabel = new Label("Add Customer");
        Label customerIDLabel = new Label("ID:");
        Label customerNameLabel = new Label("Name:");
        
        TextField customerIDField = new TextField();
        TextField customerNameField = new TextField();
        
        Button addCustomerButton = new Button("Add Customer");
        Label vehicleCustomerMessageLabel = new Label();
        
        addTabGrid.add(addCustomerLabel, 3, 0);
        
        addTabGrid.add(customerIDLabel, 2, 1);
        addTabGrid.add(customerIDField, 3, 1);
        
        addTabGrid.add(customerNameLabel, 2, 2);
        addTabGrid.add(customerNameField, 3, 2);
        
        addTabGrid.add(addCustomerButton, 3, 3);
        addTabGrid.add(vehicleCustomerMessageLabel, 3, 4);
        
        
        addVehicleButton.setOnAction(e -> {
            String type = vehicleTypeDropdown.getValue();
            String plate = vehiclePlateField.getText().toUpperCase();
            String make = vehicleMakeField.getText();
            String model = vehicleModelField.getText();
            String yearStr = vehicleYearField.getText();
            
            if (type == "Choose a Value" || plate.isEmpty() || make.isEmpty() || model.isEmpty() || yearStr.isEmpty()) {
            	vehicleAddMessageLabel.setText("Please set all values");
            	return;
            }
            if (!Vehicle.isValidPlate(plate)) {
            	vehicleAddMessageLabel.setText("Invalid Licence plate");
            	return;
            }
            if (rentalSystem.findVehicleByPlate(plate) != null) {
            	vehicleAddMessageLabel.setText("A vehicle with this plate exist in the system");
            	return;
            }
            if (!yearStr.matches("\\d+")) {
            	vehicleAddMessageLabel.setText("Must be a valid year");
            	return;
            }
        	int year = Integer.parseInt(yearStr);
        	
        	Vehicle vehicle = null;
        	String seatsStr = "";
        	int seats = 0;
        	switch (type) {
        		case "Car":
        			seatsStr = fillableField.getText();
        			if (!seatsStr.matches("\\d+")) {
        				vehicleAddMessageLabel.setText("Must be a valid number of seats");
                    	return;
        			}
        			seats = Integer.parseInt(seatsStr);
        			
        			vehicle = new Car(make, model, year, seats);
        			break;
        		case "Minibus":
        			boolean isAccessible = booleanBox.isSelected();
        			vehicle = new Minibus(make, model, year, isAccessible);
        			break;
        		case "Pickup Truck":
        			String cargoSizeStr = fillableField.getText();
        			if (!cargoSizeStr.matches("\\d+(\\.\\d+)?")) {
        				vehicleAddMessageLabel.setText("Must be a valid cargoSize");
                    	return;
        			}
        			double cargoSize = Double.parseDouble(seatsStr);
        			boolean hasTrailer = booleanBox.isSelected();
        			vehicle = new PickupTruck(make, model, year, cargoSize, hasTrailer);
        			break;
        		case "Sport Car":
        			seatsStr = fillableField.getText();
        			if (!seatsStr.matches("\\d+")) {
        				vehicleAddMessageLabel.setText("Must be a valid number of seats");
                    	return;
        			}
        			seats = Integer.parseInt(seatsStr);
        			
        			String horsepowerStr = vehicleHorsepowerField.getText();
        			if (!horsepowerStr.matches("\\d+")) {
        				vehicleAddMessageLabel.setText("Must be a valid horsepower");
                    	return;
        			}
        			int horsepower = Integer.parseInt(horsepowerStr);
        			boolean hasTurbo = booleanBox.isSelected();
        			vehicle = new SportCar(make, model, year, seats, horsepower, hasTurbo);
        			break;
        		default:
        			vehicleAddMessageLabel.setText("You should not be seeing this, like really idk how you got this");
        			vehicle = null;
                	break;
        	}
        	if (vehicle == null) {
        		vehicleAddMessageLabel.setText("Vehicle not added successfully");
        		return;
        	}
        	
        	vehicle.setLicensePlate(plate);
            rentalSystem.addVehicle(vehicle);
            
            vehicleAddMessageLabel.setText("Vehicle added successfully");
            
            availableVehiclesPane.setContent(new TextArea(getVehicleLogs(Vehicle.VehicleStatus.Available)));
        	rentedVehiclesPane.setContent(new TextArea(getVehicleLogs(Vehicle.VehicleStatus.Rented)));
        	rentalHistoryPane.setContent(new TextArea(getRentalLogs()));
        });

        addCustomerButton.setOnAction(e -> {
        	String customerIDStr = customerIDField.getText();
            String customerName = customerNameField.getText();
            
            if (customerIDStr.isEmpty() || customerName.isEmpty()) {
            	vehicleCustomerMessageLabel.setText("Please set all values");
            	return;
            }
            
            if (!customerIDStr.matches("\\d+")) {
            	vehicleCustomerMessageLabel.setText("Customer ID must be a valid number");
            	return;
            }
            int customerID = Integer.parseInt(customerIDStr);
            
        	if (rentalSystem.addCustomer(new Customer(customerID, customerName))) {
        		vehicleCustomerMessageLabel.setText("Customer added successfully");
        		availableVehiclesPane.setContent(new TextArea(getVehicleLogs(Vehicle.VehicleStatus.Available)));
            	rentedVehiclesPane.setContent(new TextArea(getVehicleLogs(Vehicle.VehicleStatus.Rented)));
            	rentalHistoryPane.setContent(new TextArea(getRentalLogs()));
        	}
        	else
        		vehicleCustomerMessageLabel.setText("Customer failed to be added");
        });

        
        addTab.setContent(addTabGrid);

        // ===== MANAGE TAB =====
        Tab manageTab = new Tab("Manage");

        GridPane manageTabgrid = new GridPane();
        manageTabgrid.setHgap(10);
        manageTabgrid.setVgap(10);
        manageTabgrid.setPadding(new Insets(20));

        // Labels
        Label availableVehiclesLabel = new Label("Available Vehicles");
        Label rentedVehiclesLabel = new Label("Rented Vehicles");
        
        // Layout placement
        manageTabgrid.add(availableVehiclesLabel, 0, 0);
        manageTabgrid.add(availableVehiclesPane, 0, 1, 1, 4);

        manageTabgrid.add(rentedVehiclesLabel, 0, 5);
        manageTabgrid.add(rentedVehiclesPane, 0, 6, 1, 5);
        
        Label rentVehicleLabel = new Label("Rent Vehicle");
        Label rentPlateLabel = new Label("License Plate:");
        Label rentalAmountLabel = new Label("Rental Amount ($):");
        Label rentCustomerLabel = new Label("Customer ID:");
        
        Label returnVehicleLabel = new Label("Return Vehicle");
        Label returnPlateLabel = new Label("License Plate:");
        Label returnCustomerLabel = new Label("Customer ID:");
        Label returnFeesLabel = new Label("Fees:");
        
        
        TextField rentPlateField = new TextField();
        TextField rentalAmountField = new TextField();
        TextField rentCustomerField = new TextField();
        
        Button rentVehicleButton = new Button("Rent Vehicle");
        Label rentVehicleMessageLabel = new Label();
        
        TextField returnPlateField = new TextField();
        TextField returnCustomerField = new TextField();
        TextField returnFeesField = new TextField();
        
        Button returnVehicleButton = new Button("Return Vehicle");
        Label returnVehicleMessageLabel = new Label();
        
        
        manageTabgrid.add(rentVehicleLabel, 3, 0);
        
        manageTabgrid.add(rentPlateLabel, 2, 1);
        manageTabgrid.add(rentPlateField, 3, 1);
        
        manageTabgrid.add(rentalAmountLabel, 2, 2);
        manageTabgrid.add(rentalAmountField, 3, 2);
        
        manageTabgrid.add(rentCustomerLabel, 2, 3);
        manageTabgrid.add(rentCustomerField, 3, 3);
        
        manageTabgrid.add(rentVehicleButton, 3, 4);
        manageTabgrid.add(rentVehicleMessageLabel, 3, 5);
        
        manageTabgrid.add(returnVehicleLabel, 3, 6);
        
        manageTabgrid.add(returnPlateLabel, 2, 7);
        manageTabgrid.add(returnPlateField, 3, 7);
        
        manageTabgrid.add(returnCustomerLabel, 2, 8);
        manageTabgrid.add(returnCustomerField, 3, 8);
        
        manageTabgrid.add(returnFeesLabel, 2, 9);
        manageTabgrid.add(returnFeesField, 3, 9);
        
        manageTabgrid.add(returnVehicleButton, 3, 10);
        manageTabgrid.add(returnVehicleMessageLabel, 3, 11);

        rentVehicleButton.setOnAction(e -> {
        	String plate = rentPlateField.getText().toUpperCase();
            String priceStr = rentalAmountField.getText();
            String iDStr = rentCustomerField.getText();
            
            if (plate.isEmpty() || priceStr.isEmpty() || iDStr.isEmpty()) {
            	rentVehicleMessageLabel.setText("Please set all values");
            	return;
            }
            
            if (!Vehicle.isValidPlate(plate)) {
            	rentVehicleMessageLabel.setText("Invalid Licence plate");
            	return;
            }
            if (!priceStr.matches("\\d+(\\.\\d+)?")) {
            	rentVehicleMessageLabel.setText("Must be a valid price");
            	return;
			}
            double price = Double.parseDouble(priceStr);
            if (!iDStr.matches("\\d+")) {
            	rentVehicleMessageLabel.setText("Must be a valid ID");
            	return;
			}
            int iD = Integer.parseInt(iDStr);
            
            Vehicle vehicleToRent = rentalSystem.findVehicleByPlate(plate);
            if (vehicleToRent == null) {
            	rentVehicleMessageLabel.setText("Vehicle not found");
            	return;
            }
            Customer customerToRent = rentalSystem.findCustomerById(iD);
            if (customerToRent == null) {
            	rentVehicleMessageLabel.setText("Customer not found");
            	return;
            }
            
            if (rentalSystem.rentVehicle(vehicleToRent, customerToRent, LocalDate.now(), price)) {
            	rentVehicleMessageLabel.setText("Vehicle rented successfully");
            	availableVehiclesPane.setContent(new TextArea(getVehicleLogs(Vehicle.VehicleStatus.Available)));
            	rentedVehiclesPane.setContent(new TextArea(getVehicleLogs(Vehicle.VehicleStatus.Rented)));
            	rentalHistoryPane.setContent(new TextArea(getRentalLogs()));
            }
        	else
        		rentVehicleMessageLabel.setText("Vehicle failed to be rented");
        });
        
        returnVehicleButton.setOnAction(e -> {
        	String plate = returnPlateField.getText();
        	String iDStr = returnCustomerField.getText();
            String feesStr = returnFeesField.getText();
            
            if (plate.isEmpty() || iDStr.isEmpty() || feesStr.isEmpty()) {
            	returnVehicleMessageLabel.setText("Please set all values");
            	return;
            }
            
            if (!Vehicle.isValidPlate(plate)) {
            	returnVehicleMessageLabel.setText("Invalid licence plate");
            	return;
            }
            if (!iDStr.matches("\\d+")) {
            	returnVehicleMessageLabel.setText("Must be a valid ID");
            	return;
			}
            int iD = Integer.parseInt(iDStr);
            if (!feesStr.matches("\\d+(\\.\\d+)?")) {
            	returnVehicleMessageLabel.setText("Must be a valid fee");
            	return;
			}
            double fees = Double.parseDouble(feesStr);
            
            Vehicle vehicleToReturn = rentalSystem.findVehicleByPlate(plate);
            Customer customerToReturn = rentalSystem.findCustomerById(iD);
            
            if (rentalSystem.returnVehicle(vehicleToReturn, customerToReturn, LocalDate.now(), fees)) {
            	returnVehicleMessageLabel.setText("Vehicle returned successfully");
            	availableVehiclesPane.setContent(new TextArea(getVehicleLogs(Vehicle.VehicleStatus.Available)));
            	rentedVehiclesPane.setContent(new TextArea(getVehicleLogs(Vehicle.VehicleStatus.Rented)));
            	rentalHistoryPane.setContent(new TextArea(getRentalLogs()));
            } 
            else {
            	returnVehicleMessageLabel.setText("Vehicle failed to be returned");
            }
        });

        manageTab.setContent(manageTabgrid);

        // ===== HISTORY TAB =====
        Tab historyTab = new Tab("History");
        
        GridPane historyTabGrid = new GridPane();
        historyTabGrid.setHgap(10);
        historyTabGrid.setVgap(10);
        historyTabGrid.setPadding(new Insets(20));
        
        Label rentalHistoryLabel = new Label("Rental History");
        
        historyTabGrid.add(rentalHistoryLabel, 0, 0);
        historyTabGrid.add(rentalHistoryPane, 0, 2);
        
        historyTab.setContent(historyTabGrid);

        // Add all tabs
        tabPane.getTabs().addAll(addTab, manageTab, historyTab);

        // Scene
        Scene scene = new Scene(tabPane, 500, 350);
        primaryStage.setTitle("Rental System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
    	 
        launch(args);
    }
    
    private String getRentalLogs() {
    	String logs = "";
    	if (rentalSystem.getRentalHistory().getRentalHistory().isEmpty()) {
    		logs += String.format("  No rental history found.");
        } else {
            // Header with proper column widths
        	logs += String.format("|%-10s | %-12s | %-20s | %-12s | %-12s |%n", 
                " Type", "Plate", "Customer", "Date", "Amount");
        	logs += String.format("|---------------------------------------------------------------------------------------------|%n");
            
            for (RentalRecord record : rentalSystem.getRentalHistory().getRentalHistory()) {                
            	logs += String.format("| %-9s | %-12s | %-20s | %-12s | $%-11.2f |%n", 
                    record.getRecordType(), 
                    record.getVehicle().getLicensePlate(),
                    record.getCustomer().getCustomerName(),
                    record.getRecordDate().toString(),
                    record.getTotalAmount()
                );
            }
        }
    	return logs;
    }
    
    private void update() {
    	
    }
    
    private String getVehicleLogs(Vehicle.VehicleStatus status) {
    	String logs = "";
        
        // Header with proper column widths
        logs += String.format("|%-16s | %-12s | %-12s | %-12s | %-6s | %-18s |%n", 
            " Type", "Plate", "Make", "Model", "Year", "Status");
        logs += String.format("|-------------------------------------------------------------------------------------------------------|%n");
    	  
        boolean found = false;
        for (Vehicle vehicle : rentalSystem.getVehicles()) {
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
                logs += String.format("| %-15s | %-12s | %-12s | %-12s | %-6d | %-18s |%n", 
                    vehicleType, vehicle.getLicensePlate(), vehicle.getMake(), vehicle.getModel(), vehicle.getYear(), vehicle.getStatus().toString());
            }
        }
        if (!found) {
            if (status == null) {
            	logs += String.format("  No Vehicles found.");
            } else {
            	logs += String.format("  No vehicles with Status: " + status);
            }
        }
        return logs;
    }
}