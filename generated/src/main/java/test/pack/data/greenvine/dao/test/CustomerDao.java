package test.pack.data.greenvine.dao.test;

import java.util.List;
import test.pack.data.greenvine.entity.test.Customer;
import test.pack.data.greenvine.entity.test.PersonIdentity;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface CustomerDao {
	
    /**
	 * Returns the Customer with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param person the value of the identity
	 * @return Customer with matching identity
	 */
	public abstract Customer loadCustomer(PersonIdentity person);
    
    /**
	 * Returns the Customer with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param person the value of the identity
	 * @return Customer with matching identity (or null)
	 */
	public abstract Customer findCustomer(PersonIdentity person);
  
	/**
	 * Retrieve all Customer objects
	 * 
	 * @return List<Customer>of all Customer 
	 * objects in the database. 
	 */
	public abstract List<Customer> findAllCustomers();
			
	/**
	 * Update a supplied Customer loaded in a separate transaction
	 * @param customer the Customer to update
	 */
	public abstract void updateCustomer(Customer customer);
	
	/**
	 * Create a supplied Customer object
	 * @param customer the Customer to create
	 */
	public abstract void createCustomer(Customer customer);
    
    /**
	 * Remove the Customer with the specified identity
	 * @param person the value of the identity
	 */
	public abstract void removeCustomer(PersonIdentity person);
	

}