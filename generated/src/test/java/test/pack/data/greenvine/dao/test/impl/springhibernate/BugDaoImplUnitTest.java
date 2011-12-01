package test.pack.data.greenvine.dao.test.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Bug;
import test.pack.data.greenvine.entity.test.BugTestUtils;

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
public class BugDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private BugDaoImpl bugDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Bug expected = BugTestUtils.createDefaultInstance();
        session.returns(expected).load(Bug.class, BugTestUtils.getDefaultIdentity());
        Bug actual = bugDao.loadBug(BugTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Bug.class, BugTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Bug.class, BugTestUtils.getDefaultIdentity());
        bugDao.loadBug(BugTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Bug expected = BugTestUtils.createDefaultInstance();
        session.returns(expected).get(Bug.class, BugTestUtils.getDefaultIdentity());
        Bug actual = bugDao.findBug(BugTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Bug.class, BugTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Bug.class, BugTestUtils.getDefaultIdentity());
        Bug actual = bugDao.findBug(BugTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Bug expected = BugTestUtils.createDefaultInstance();
        bugDao.createBug(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Bug expected = BugTestUtils.createDefaultInstance();
        bugDao.updateBug(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Bug expected = BugTestUtils.createDefaultInstance();
        session.returns(expected).load(Bug.class, BugTestUtils.getDefaultIdentity());
        bugDao.removeBug(BugTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}