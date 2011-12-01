package test.pack.data.greenvine.dao.test.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Activity;
import test.pack.data.greenvine.entity.test.ActivityTestUtils;

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
public class ActivityDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private ActivityDaoImpl activityDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Activity expected = ActivityTestUtils.createDefaultInstance();
        session.returns(expected).load(Activity.class, ActivityTestUtils.getDefaultIdentity());
        Activity actual = activityDao.loadActivity(ActivityTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Activity.class, ActivityTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Activity.class, ActivityTestUtils.getDefaultIdentity());
        activityDao.loadActivity(ActivityTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Activity expected = ActivityTestUtils.createDefaultInstance();
        session.returns(expected).get(Activity.class, ActivityTestUtils.getDefaultIdentity());
        Activity actual = activityDao.findActivity(ActivityTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Activity.class, ActivityTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Activity.class, ActivityTestUtils.getDefaultIdentity());
        Activity actual = activityDao.findActivity(ActivityTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Activity expected = ActivityTestUtils.createDefaultInstance();
        activityDao.createActivity(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Activity expected = ActivityTestUtils.createDefaultInstance();
        activityDao.updateActivity(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Activity expected = ActivityTestUtils.createDefaultInstance();
        session.returns(expected).load(Activity.class, ActivityTestUtils.getDefaultIdentity());
        activityDao.removeActivity(ActivityTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}