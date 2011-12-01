package test.pack.data.greenvine.dao.test.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Timesheet;
import test.pack.data.greenvine.entity.test.TimesheetTestUtils;

import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TimesheetDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private TimesheetDaoImpl timesheetDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Timesheet expected = TimesheetTestUtils.createDefaultInstance();
        session.returns(expected).load(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        Timesheet actual = timesheetDao.loadTimesheet(TimesheetTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        timesheetDao.loadTimesheet(TimesheetTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Timesheet expected = TimesheetTestUtils.createDefaultInstance();
        session.returns(expected).get(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        Timesheet actual = timesheetDao.findTimesheet(TimesheetTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        Timesheet actual = timesheetDao.findTimesheet(TimesheetTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Timesheet expected = TimesheetTestUtils.createDefaultInstance();
        timesheetDao.createTimesheet(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Timesheet expected = TimesheetTestUtils.createDefaultInstance();
        timesheetDao.updateTimesheet(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Timesheet expected = TimesheetTestUtils.createDefaultInstance();
        session.returns(expected).load(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        timesheetDao.removeTimesheet(TimesheetTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}