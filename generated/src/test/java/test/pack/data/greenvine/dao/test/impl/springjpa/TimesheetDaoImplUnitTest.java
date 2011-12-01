package test.pack.data.greenvine.dao.test.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Timesheet;
import test.pack.data.greenvine.entity.test.TimesheetTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TimesheetDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private TimesheetDaoImpl timesheetDao;
    
    @Test
    public void testLoadExisting() {
    
        Timesheet expected = TimesheetTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        Timesheet actual = timesheetDao.loadTimesheet(TimesheetTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        timesheetDao.loadTimesheet(TimesheetTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Timesheet expected = TimesheetTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        Timesheet actual = timesheetDao.findTimesheet(TimesheetTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        Timesheet actual = timesheetDao.findTimesheet(TimesheetTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Timesheet expected = TimesheetTestUtils.createDefaultInstance();
        timesheetDao.createTimesheet(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Timesheet expected = TimesheetTestUtils.createDefaultInstance();
        timesheetDao.updateTimesheet(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Timesheet expected = TimesheetTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        timesheetDao.removeTimesheet(TimesheetTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}