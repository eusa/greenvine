package test.pack.data.greenvine.dao.dbo;

import java.util.List;
import test.pack.data.greenvine.entity.dbo.Stand;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface StandDao {
	
    /**
	 * Returns the Stand with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param standId the value of the identity
	 * @return Stand with matching identity
	 */
	public abstract Stand loadStand(Integer standId);
    
    /**
	 * Returns the Stand with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param standId the value of the identity
	 * @return Stand with matching identity (or null)
	 */
	public abstract Stand findStand(Integer standId);
  
	/**
	 * Retrieve all Stand objects
	 * 
	 * @return List<Stand>of all Stand 
	 * objects in the database. 
	 */
	public abstract List<Stand> findAllStands();
			
	/**
	 * Update a supplied Stand loaded in a separate transaction
	 * @param stand the Stand to update
	 */
	public abstract void updateStand(Stand stand);
	
	/**
	 * Create a supplied Stand object
	 * @param stand the Stand to create
	 */
	public abstract void createStand(Stand stand);
    
    /**
	 * Remove the Stand with the specified identity
	 * @param standId the value of the identity
	 */
	public abstract void removeStand(Integer standId);
	

}