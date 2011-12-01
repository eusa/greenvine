package test.pack.data.greenvine.dao.test;

import java.util.List;
import test.pack.data.greenvine.entity.test.Person;
import test.pack.data.greenvine.entity.test.PersonIdentity;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface PersonDao {
	
    /**
	 * Returns the Person with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param personIdentity the value of the identity
	 * @return Person with matching identity
	 */
	public abstract Person loadPerson(PersonIdentity personIdentity);
    
    /**
	 * Returns the Person with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param personIdentity the value of the identity
	 * @return Person with matching identity (or null)
	 */
	public abstract Person findPerson(PersonIdentity personIdentity);
  
	/**
	 * Retrieve all Person objects
	 * 
	 * @return List<Person>of all Person 
	 * objects in the database. 
	 */
	public abstract List<Person> findAllPersons();
			
	/**
	 * Update a supplied Person loaded in a separate transaction
	 * @param person the Person to update
	 */
	public abstract void updatePerson(Person person);
	
	/**
	 * Create a supplied Person object
	 * @param person the Person to create
	 */
	public abstract void createPerson(Person person);
    
    /**
	 * Remove the Person with the specified identity
	 * @param personIdentity the value of the identity
	 */
	public abstract void removePerson(PersonIdentity personIdentity);
	

}