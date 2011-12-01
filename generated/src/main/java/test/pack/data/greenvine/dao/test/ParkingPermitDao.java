package test.pack.data.greenvine.dao.test;

import java.util.List;
import test.pack.data.greenvine.entity.test.ParkingPermit;
import test.pack.data.greenvine.entity.test.ParkingPermitIdentity;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface ParkingPermitDao {
	
    /**
	 * Returns the ParkingPermit with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param parkingPermitIdentity the value of the identity
	 * @return ParkingPermit with matching identity
	 */
	public abstract ParkingPermit loadParkingPermit(ParkingPermitIdentity parkingPermitIdentity);
    
    /**
	 * Returns the ParkingPermit with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param parkingPermitIdentity the value of the identity
	 * @return ParkingPermit with matching identity (or null)
	 */
	public abstract ParkingPermit findParkingPermit(ParkingPermitIdentity parkingPermitIdentity);
  
	/**
	 * Retrieve all ParkingPermit objects
	 * 
	 * @return List<ParkingPermit>of all ParkingPermit 
	 * objects in the database. 
	 */
	public abstract List<ParkingPermit> findAllParkingPermits();
			
	/**
	 * Update a supplied ParkingPermit loaded in a separate transaction
	 * @param parkingPermit the ParkingPermit to update
	 */
	public abstract void updateParkingPermit(ParkingPermit parkingPermit);
	
	/**
	 * Create a supplied ParkingPermit object
	 * @param parkingPermit the ParkingPermit to create
	 */
	public abstract void createParkingPermit(ParkingPermit parkingPermit);
    
    /**
	 * Remove the ParkingPermit with the specified identity
	 * @param parkingPermitIdentity the value of the identity
	 */
	public abstract void removeParkingPermit(ParkingPermitIdentity parkingPermitIdentity);
	

}