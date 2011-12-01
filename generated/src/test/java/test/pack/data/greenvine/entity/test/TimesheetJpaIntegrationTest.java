package test.pack.data.greenvine.entity.test;

import java.util.List;
import java.math.BigDecimal;
import test.pack.data.greenvine.entity.test.TimesheetIdentity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import test.pack.data.greenvine.entity.test.Timesheet;
import test.pack.data.greenvine.entity.test.TimesheetTestUtils;
import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.dbo.EmployeeTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TimesheetJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("TimesheetBeforeCreateDataSet.xml")
    @ExpectedDataSet("TimesheetAfterCreateDataSet.xml")
    public void testCreateTimesheet() throws Exception {
    
        // Create new entity
        Timesheet create = new Timesheet();

        // Set identity
        TimesheetIdentity timesheetIdentity = new TimesheetIdentity();
        TimesheetTestUtils.populateAssignedIdentityFields(timesheetIdentity);
        create.setTimesheetIdentity(timesheetIdentity);

        // Populate simple properties
        create.setExpectedHours(new BigDecimal("100.1"));
                
        // Populate dependencies
        Employee employee = (Employee)entityManager.getReference(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        create.setEmployee(employee);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("TimesheetBeforeUpdateDataSet.xml")
    @ExpectedDataSet("TimesheetAfterUpdateDataSet.xml")
    public void testUpdateTimesheet() throws Exception {
                    
        // Load entity and modify
        Timesheet result = (Timesheet)entityManager.find(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setExpectedHours(new BigDecimal("200.2"));

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("TimesheetBeforeDeleteDataSet.xml")
    @ExpectedDataSet("TimesheetAfterDeleteDataSet.xml")
    public void testRemoveTimesheet() throws Exception {
                    
        // Delete
        Timesheet result = (Timesheet)entityManager.find(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("TimesheetFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllTimesheet() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from test.Timesheet");
                    
        // Get results 
        List<Timesheet> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("TimesheetFindDataSet.xml")
    public void testFindTimesheetByIdentity() throws Exception {
                    
        // Get object 
        Timesheet result  = (Timesheet)entityManager.find(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals(new BigDecimal("100.1"), result.getExpectedHours());

    }
    
}