package test.pack.data.greenvine.dao.test;

import java.util.List;
import test.pack.data.greenvine.entity.test.Bug;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface BugDao {
	
    /**
	 * Returns the Bug with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param bugId the value of the identity
	 * @return Bug with matching identity
	 */
	public abstract Bug loadBug(Integer bugId);
    
    /**
	 * Returns the Bug with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param bugId the value of the identity
	 * @return Bug with matching identity (or null)
	 */
	public abstract Bug findBug(Integer bugId);
  
	/**
	 * Retrieve all Bug objects
	 * 
	 * @return List<Bug>of all Bug 
	 * objects in the database. 
	 */
	public abstract List<Bug> findAllBugs();
			
	/**
	 * Update a supplied Bug loaded in a separate transaction
	 * @param bug the Bug to update
	 */
	public abstract void updateBug(Bug bug);
	
	/**
	 * Create a supplied Bug object
	 * @param bug the Bug to create
	 */
	public abstract void createBug(Bug bug);
    
    /**
	 * Remove the Bug with the specified identity
	 * @param bugId the value of the identity
	 */
	public abstract void removeBug(Integer bugId);
	

}