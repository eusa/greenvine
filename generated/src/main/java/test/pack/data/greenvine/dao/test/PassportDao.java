package test.pack.data.greenvine.dao.test;

import java.util.List;
import test.pack.data.greenvine.entity.test.Passport;
import java.lang.String;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface PassportDao {
	
    /**
	 * Returns the Passport with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param passportNr the value of the identity
	 * @return Passport with matching identity
	 */
	public abstract Passport loadPassport(String passportNr);
    
    /**
	 * Returns the Passport with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param passportNr the value of the identity
	 * @return Passport with matching identity (or null)
	 */
	public abstract Passport findPassport(String passportNr);
  
	/**
	 * Retrieve all Passport objects
	 * 
	 * @return List<Passport>of all Passport 
	 * objects in the database. 
	 */
	public abstract List<Passport> findAllPassports();
			
	/**
	 * Update a supplied Passport loaded in a separate transaction
	 * @param passport the Passport to update
	 */
	public abstract void updatePassport(Passport passport);
	
	/**
	 * Create a supplied Passport object
	 * @param passport the Passport to create
	 */
	public abstract void createPassport(Passport passport);
    
    /**
	 * Remove the Passport with the specified identity
	 * @param passportNr the value of the identity
	 */
	public abstract void removePassport(String passportNr);
	

}