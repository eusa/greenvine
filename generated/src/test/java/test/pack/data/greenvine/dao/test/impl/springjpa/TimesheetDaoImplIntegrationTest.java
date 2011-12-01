package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;
import java.math.BigDecimal;
import test.pack.data.greenvine.entity.test.TimesheetIdentity;

import org.junit.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import test.pack.data.greenvine.entity.test.Timesheet;
import test.pack.data.greenvine.entity.test.TimesheetTestUtils;
import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.dbo.EmployeeTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TimesheetDaoImplIntegrationTest {
    
    @TestedObject
    private TimesheetDaoImpl timesheetDao = null;

    @PersistenceContext
    @InjectInto(property="entityManager")
    private EntityManager entityManager;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/TimesheetBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/TimesheetAfterCreateDataSet.xml")
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
        timesheetDao.createTimesheet(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/TimesheetBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/TimesheetAfterUpdateDataSet.xml")
    public void testUpdateTimesheet() throws Exception {
        
         // Load entity and modify
        Timesheet update = timesheetDao.loadTimesheet(TimesheetTestUtils.getDefaultIdentity());
        update.setExpectedHours(new BigDecimal("200.2"));

        // Update entity
        timesheetDao.updateTimesheet(update);

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/TimesheetBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/TimesheetAfterDeleteDataSet.xml")
    public void testRemoveTimesheet() throws Exception {

        // Delete by id
        timesheetDao.removeTimesheet(TimesheetTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/TimesheetFindAllDataSet.xml")
    public void testFindAllTimesheets() throws Exception {
    
        List<Timesheet> results = timesheetDao.findAllTimesheets();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/TimesheetFindDataSet.xml")
    public void testLoadTimesheetByIdentity() throws Exception {
    
        Timesheet result = timesheetDao.loadTimesheet(TimesheetTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals(new BigDecimal("100.1"), result.getExpectedHours());

    }

}