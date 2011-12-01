package test.pack.data.greenvine.entity.test;

import java.util.Date;

public class ParkingPermitPaymentTestUtils {
    
    public static ParkingPermitPayment createDefaultInstance() {
    
        // Create new entity
        ParkingPermitPayment create = new ParkingPermitPayment();
                    
        // Set identity
        create.setParkingPermitIdentity(getDefaultIdentity());

        // Populate simple properties
        create.setPaymentDate(new Date(1230771661000L));
                
        // Populate dependencies
        ParkingPermit parkingPermit = ParkingPermitTestUtils.createDefaultInstance();
        create.setParkingPermit(parkingPermit);

        // Return instance
        return create;

    }
    
    public static ParkingPermitIdentity getDefaultIdentity() {
    
        ParkingPermitIdentity parkingPermit = new ParkingPermitIdentity();
        parkingPermit.setDate(new Date(1230768000000L));
        parkingPermit.setRegNumber("s");
        return parkingPermit;
    
    }

    
    
    public static void populateAssignedIdentityFields(ParkingPermitIdentity parkingPermit) {
    
        parkingPermit.setDate(new Date(1230768000000L));
        parkingPermit.setRegNumber("s");
          
    }
    
    public static ParkingPermitPayment createRandomInstance() {
    
        // create new entity
        ParkingPermitPayment create = new ParkingPermitPayment();
                    
        // set identity
        create.setParkingPermitIdentity(getRandomIdentity());
        
        // populate simple properties
        create.setPaymentDate(new Date(1233540122000L));
                
        // populate dependencies
        ParkingPermit parkingPermit = ParkingPermitTestUtils.createRandomInstance();
        create.setParkingPermit(parkingPermit);

        // return instance
        return create;

    }
    
    public static ParkingPermitIdentity getRandomIdentity() {
    
        ParkingPermitIdentity parkingPermit = new ParkingPermitIdentity();
        parkingPermit.setDate(new Date(1233532800000L));
        parkingPermit.setRegNumber("t");
        return parkingPermit;
    
    }

    /**
    * Creates a deep copy
    * of the object including 
    * all dependent fields. 
    */
    public static ParkingPermitPayment clone(ParkingPermitPayment parkingPermitPayment){

        ParkingPermitPayment _parkingPermitPayment = new ParkingPermitPayment();
        
        if (parkingPermitPayment.getParkingPermitIdentity() != null) {
            ParkingPermitIdentity parkingPermitIdentity = parkingPermitPayment.getParkingPermitIdentity();
            ParkingPermitIdentity _parkingPermitIdentity = new ParkingPermitIdentity();
            _parkingPermitIdentity.setDate(new Date(parkingPermitIdentity.getDate().getTime()));
            _parkingPermitIdentity.setRegNumber(parkingPermitIdentity.getRegNumber());
            _parkingPermitPayment.setParkingPermitIdentity(_parkingPermitIdentity);
        }
        if (parkingPermitPayment.getPaymentDate() != null) {
            _parkingPermitPayment.setPaymentDate(new Date(parkingPermitPayment.getPaymentDate().getTime()));
        }
        if (parkingPermitPayment.getParkingPermit() != null) {
            _parkingPermitPayment.setParkingPermit(ParkingPermitTestUtils.clone(parkingPermitPayment.getParkingPermit()));
        }

        return _parkingPermitPayment;
    }
    
}