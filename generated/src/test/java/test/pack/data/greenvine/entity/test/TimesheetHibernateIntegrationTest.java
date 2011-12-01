package test.pack.data.greenvine.entity.test;

import java.util.List;
import java.math.BigDecimal;
import test.pack.data.greenvine.entity.test.TimesheetIdentity;

import org.junit.Assert;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.test.Timesheet;
import test.pack.data.greenvine.entity.test.TimesheetTestUtils;
import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.dbo.EmployeeTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TimesheetHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
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
         
  
        // populate simple properties
        create.setExpectedHours(new BigDecimal("100.1"));
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
                
        // populate dependencies
        Employee employee = (Employee)session.get(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        create.setEmployee(employee);

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("TimesheetBeforeUpdateDataSet.xml")
    @ExpectedDataSet("TimesheetAfterUpdateDataSet.xml")
    public void testUpdateTimesheet() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Timesheet result = (Timesheet)session.get(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        result.setExpectedHours(new BigDecimal("200.2"));
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("TimesheetBeforeDeleteDataSet.xml")
    @ExpectedDataSet("TimesheetAfterDeleteDataSet.xml")
    public void testRemoveTimesheet() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Timesheet result = (Timesheet)session.get(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("TimesheetFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllTimesheets() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.test.Timesheet");
        
        // Get results 
        List<Timesheet> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("TimesheetFindDataSet.xml")
    public void testFindTimesheetByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Timesheet result  = (Timesheet)session.get(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals(new BigDecimal("100.1"), result.getExpectedHours());

    }
    
}