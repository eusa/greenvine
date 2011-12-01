package test.pack.data.greenvine.dao.test;

import java.util.List;
import test.pack.data.greenvine.entity.test.Activity;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface ActivityDao {
	
    /**
	 * Returns the Activity with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param activityId the value of the identity
	 * @return Activity with matching identity
	 */
	public abstract Activity loadActivity(Integer activityId);
    
    /**
	 * Returns the Activity with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param activityId the value of the identity
	 * @return Activity with matching identity (or null)
	 */
	public abstract Activity findActivity(Integer activityId);
  
	/**
	 * Retrieve all Activity objects
	 * 
	 * @return List<Activity>of all Activity 
	 * objects in the database. 
	 */
	public abstract List<Activity> findAllActivitys();
			
	/**
	 * Update a supplied Activity loaded in a separate transaction
	 * @param activity the Activity to update
	 */
	public abstract void updateActivity(Activity activity);
	
	/**
	 * Create a supplied Activity object
	 * @param activity the Activity to create
	 */
	public abstract void createActivity(Activity activity);
    
    /**
	 * Remove the Activity with the specified identity
	 * @param activityId the value of the identity
	 */
	public abstract void removeActivity(Integer activityId);
	

}