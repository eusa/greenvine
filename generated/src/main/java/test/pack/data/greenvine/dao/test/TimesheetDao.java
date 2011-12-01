package test.pack.data.greenvine.dao.test;

import java.util.List;
import test.pack.data.greenvine.entity.test.Timesheet;
import test.pack.data.greenvine.entity.test.TimesheetIdentity;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface TimesheetDao {
	
    /**
	 * Returns the Timesheet with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param timesheetIdentity the value of the identity
	 * @return Timesheet with matching identity
	 */
	public abstract Timesheet loadTimesheet(TimesheetIdentity timesheetIdentity);
    
    /**
	 * Returns the Timesheet with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param timesheetIdentity the value of the identity
	 * @return Timesheet with matching identity (or null)
	 */
	public abstract Timesheet findTimesheet(TimesheetIdentity timesheetIdentity);
  
	/**
	 * Retrieve all Timesheet objects
	 * 
	 * @return List<Timesheet>of all Timesheet 
	 * objects in the database. 
	 */
	public abstract List<Timesheet> findAllTimesheets();
			
	/**
	 * Update a supplied Timesheet loaded in a separate transaction
	 * @param timesheet the Timesheet to update
	 */
	public abstract void updateTimesheet(Timesheet timesheet);
	
	/**
	 * Create a supplied Timesheet object
	 * @param timesheet the Timesheet to create
	 */
	public abstract void createTimesheet(Timesheet timesheet);
    
    /**
	 * Remove the Timesheet with the specified identity
	 * @param timesheetIdentity the value of the identity
	 */
	public abstract void removeTimesheet(TimesheetIdentity timesheetIdentity);
	

}