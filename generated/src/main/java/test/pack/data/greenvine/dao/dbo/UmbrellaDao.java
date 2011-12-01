package test.pack.data.greenvine.dao.dbo;

import java.util.List;
import test.pack.data.greenvine.entity.dbo.Umbrella;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface UmbrellaDao {
	
    /**
	 * Returns the Umbrella with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param umbrellaId the value of the identity
	 * @return Umbrella with matching identity
	 */
	public abstract Umbrella loadUmbrella(Integer umbrellaId);
    
    /**
	 * Returns the Umbrella with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param umbrellaId the value of the identity
	 * @return Umbrella with matching identity (or null)
	 */
	public abstract Umbrella findUmbrella(Integer umbrellaId);
  
	/**
	 * Retrieve all Umbrella objects
	 * 
	 * @return List<Umbrella>of all Umbrella 
	 * objects in the database. 
	 */
	public abstract List<Umbrella> findAllUmbrellas();
			
	/**
	 * Update a supplied Umbrella loaded in a separate transaction
	 * @param umbrella the Umbrella to update
	 */
	public abstract void updateUmbrella(Umbrella umbrella);
	
	/**
	 * Create a supplied Umbrella object
	 * @param umbrella the Umbrella to create
	 */
	public abstract void createUmbrella(Umbrella umbrella);
    
    /**
	 * Remove the Umbrella with the specified identity
	 * @param umbrellaId the value of the identity
	 */
	public abstract void removeUmbrella(Integer umbrellaId);
	

}