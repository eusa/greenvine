package test.pack.data.greenvine.dao.dbo;

import java.util.List;
import test.pack.data.greenvine.entity.dbo.Group;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface GroupDao {
	
    /**
	 * Returns the Group with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param groupId the value of the identity
	 * @return Group with matching identity
	 */
	public abstract Group loadGroup(Integer groupId);
    
    /**
	 * Returns the Group with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param groupId the value of the identity
	 * @return Group with matching identity (or null)
	 */
	public abstract Group findGroup(Integer groupId);
  
	/**
	 * Retrieve all Group objects
	 * 
	 * @return List<Group>of all Group 
	 * objects in the database. 
	 */
	public abstract List<Group> findAllGroups();
			
	/**
	 * Update a supplied Group loaded in a separate transaction
	 * @param group the Group to update
	 */
	public abstract void updateGroup(Group group);
	
	/**
	 * Create a supplied Group object
	 * @param group the Group to create
	 */
	public abstract void createGroup(Group group);
    
    /**
	 * Remove the Group with the specified identity
	 * @param groupId the value of the identity
	 */
	public abstract void removeGroup(Integer groupId);
	

}