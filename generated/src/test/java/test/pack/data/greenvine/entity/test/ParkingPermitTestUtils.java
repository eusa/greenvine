package test.pack.data.greenvine.entity.test;

import java.math.BigDecimal;
import java.util.Date;

public class ParkingPermitTestUtils {
    
    public static ParkingPermit createDefaultInstance() {
    
        // Create new entity
        ParkingPermit create = new ParkingPermit();
                    
        // Set identity
        create.setParkingPermitIdentity(getDefaultIdentity());

        // Populate simple properties
        create.setValue(new BigDecimal("100.1"));
                
        // Populate dependencies
        Vehicle vehicle = VehicleTestUtils.createDefaultInstance();
        create.setVehicle(vehicle);

        // Return instance
        return create;

    }
    
    public static ParkingPermitIdentity getDefaultIdentity() {
    
        ParkingPermitIdentity parkingPermitIdentity = new ParkingPermitIdentity();
        parkingPermitIdentity.setDate(new Date(1230768000000L));
        parkingPermitIdentity.setRegNumber("s");
        return parkingPermitIdentity;
    
    }

    
    
    public static void populateAssignedIdentityFields(ParkingPermitIdentity parkingPermitIdentity) {
    
        parkingPermitIdentity.setDate(new Date(1230768000000L));
        parkingPermitIdentity.setRegNumber("s");
          
    }
    
    public static ParkingPermit createRandomInstance() {
    
        // create new entity
        ParkingPermit create = new ParkingPermit();
                    
        // set identity
        create.setParkingPermitIdentity(getRandomIdentity());
        
        // populate simple properties
        create.setValue(new BigDecimal("200.2"));
                
        // populate dependencies
        Vehicle vehicle = VehicleTestUtils.createRandomInstance();
        create.setVehicle(vehicle);

        // return instance
        return create;

    }
    
    public static ParkingPermitIdentity getRandomIdentity() {
    
        ParkingPermitIdentity parkingPermitIdentity = new ParkingPermitIdentity();
        parkingPermitIdentity.setDate(new Date(1233532800000L));
        parkingPermitIdentity.setRegNumber("t");
        return parkingPermitIdentity;
    
    }

    /**
    * Creates a deep copy
    * of the object including 
    * all dependent fields. 
    */
    public static ParkingPermit clone(ParkingPermit parkingPermit){

        ParkingPermit _parkingPermit = new ParkingPermit();
        
        if (parkingPermit.getParkingPermitIdentity() != null) {
            ParkingPermitIdentity parkingPermitIdentity = parkingPermit.getParkingPermitIdentity();
            ParkingPermitIdentity _parkingPermitIdentity = new ParkingPermitIdentity();
            _parkingPermitIdentity.setDate(new Date(parkingPermitIdentity.getDate().getTime()));
            _parkingPermitIdentity.setRegNumber(parkingPermitIdentity.getRegNumber());
            _parkingPermit.setParkingPermitIdentity(_parkingPermitIdentity);
        }
        if (parkingPermit.getValue() != null) {
            _parkingPermit.setValue(new BigDecimal(parkingPermit.getValue().toPlainString()));
        }
        if (parkingPermit.getParkingPermitPayment() != null) {
            _parkingPermit.setParkingPermitPayment(ParkingPermitPaymentTestUtils.clone(parkingPermit.getParkingPermitPayment()));
        }
        if (parkingPermit.getVehicle() != null) {
            _parkingPermit.setVehicle(VehicleTestUtils.clone(parkingPermit.getVehicle()));
        }

        return _parkingPermit;
    }
    
}