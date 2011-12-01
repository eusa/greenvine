package test.pack.data.greenvine.dao.test;

import java.util.List;
import test.pack.data.greenvine.entity.test.Address;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface AddressDao {
	
    /**
	 * Returns the Address with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param addressId the value of the identity
	 * @return Address with matching identity
	 */
	public abstract Address loadAddress(Integer addressId);
    
    /**
	 * Returns the Address with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param addressId the value of the identity
	 * @return Address with matching identity (or null)
	 */
	public abstract Address findAddress(Integer addressId);
  
	/**
	 * Retrieve all Address objects
	 * 
	 * @return List<Address>of all Address 
	 * objects in the database. 
	 */
	public abstract List<Address> findAllAddresss();
			
	/**
	 * Update a supplied Address loaded in a separate transaction
	 * @param address the Address to update
	 */
	public abstract void updateAddress(Address address);
	
	/**
	 * Create a supplied Address object
	 * @param address the Address to create
	 */
	public abstract void createAddress(Address address);
    
    /**
	 * Remove the Address with the specified identity
	 * @param addressId the value of the identity
	 */
	public abstract void removeAddress(Integer addressId);
	

}