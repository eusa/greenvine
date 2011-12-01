package test.pack.data.greenvine.dao.test;

import java.util.List;
import test.pack.data.greenvine.entity.test.Vehicle;
import java.lang.String;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface VehicleDao {
	
    /**
	 * Returns the Vehicle with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param regNumber the value of the identity
	 * @return Vehicle with matching identity
	 */
	public abstract Vehicle loadVehicle(String regNumber);
    
    /**
	 * Returns the Vehicle with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param regNumber the value of the identity
	 * @return Vehicle with matching identity (or null)
	 */
	public abstract Vehicle findVehicle(String regNumber);
  
	/**
	 * Retrieve all Vehicle objects
	 * 
	 * @return List<Vehicle>of all Vehicle 
	 * objects in the database. 
	 */
	public abstract List<Vehicle> findAllVehicles();
			
	/**
	 * Update a supplied Vehicle loaded in a separate transaction
	 * @param vehicle the Vehicle to update
	 */
	public abstract void updateVehicle(Vehicle vehicle);
	
	/**
	 * Create a supplied Vehicle object
	 * @param vehicle the Vehicle to create
	 */
	public abstract void createVehicle(Vehicle vehicle);
    
    /**
	 * Remove the Vehicle with the specified identity
	 * @param regNumber the value of the identity
	 */
	public abstract void removeVehicle(String regNumber);
	

}