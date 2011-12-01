package test.pack.data.greenvine.dao.test;

import java.util.List;
import test.pack.data.greenvine.entity.test.ParkingPermitPayment;
import test.pack.data.greenvine.entity.test.ParkingPermitIdentity;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface ParkingPermitPaymentDao {
	
    /**
	 * Returns the ParkingPermitPayment with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param parkingPermit the value of the identity
	 * @return ParkingPermitPayment with matching identity
	 */
	public abstract ParkingPermitPayment loadParkingPermitPayment(ParkingPermitIdentity parkingPermit);
    
    /**
	 * Returns the ParkingPermitPayment with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param parkingPermit the value of the identity
	 * @return ParkingPermitPayment with matching identity (or null)
	 */
	public abstract ParkingPermitPayment findParkingPermitPayment(ParkingPermitIdentity parkingPermit);
  
	/**
	 * Retrieve all ParkingPermitPayment objects
	 * 
	 * @return List<ParkingPermitPayment>of all ParkingPermitPayment 
	 * objects in the database. 
	 */
	public abstract List<ParkingPermitPayment> findAllParkingPermitPayments();
			
	/**
	 * Update a supplied ParkingPermitPayment loaded in a separate transaction
	 * @param parkingPermitPayment the ParkingPermitPayment to update
	 */
	public abstract void updateParkingPermitPayment(ParkingPermitPayment parkingPermitPayment);
	
	/**
	 * Create a supplied ParkingPermitPayment object
	 * @param parkingPermitPayment the ParkingPermitPayment to create
	 */
	public abstract void createParkingPermitPayment(ParkingPermitPayment parkingPermitPayment);
    
    /**
	 * Remove the ParkingPermitPayment with the specified identity
	 * @param parkingPermit the value of the identity
	 */
	public abstract void removeParkingPermitPayment(ParkingPermitIdentity parkingPermit);
	

}