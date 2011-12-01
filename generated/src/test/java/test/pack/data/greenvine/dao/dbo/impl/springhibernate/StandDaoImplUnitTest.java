package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.dbo.Stand;
import test.pack.data.greenvine.entity.dbo.StandTestUtils;

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
public class StandDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private StandDaoImpl standDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Stand expected = StandTestUtils.createDefaultInstance();
        session.returns(expected).load(Stand.class, StandTestUtils.getDefaultIdentity());
        Stand actual = standDao.loadStand(StandTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Stand.class, StandTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Stand.class, StandTestUtils.getDefaultIdentity());
        standDao.loadStand(StandTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Stand expected = StandTestUtils.createDefaultInstance();
        session.returns(expected).get(Stand.class, StandTestUtils.getDefaultIdentity());
        Stand actual = standDao.findStand(StandTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Stand.class, StandTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Stand.class, StandTestUtils.getDefaultIdentity());
        Stand actual = standDao.findStand(StandTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Stand expected = StandTestUtils.createDefaultInstance();
        standDao.createStand(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Stand expected = StandTestUtils.createDefaultInstance();
        standDao.updateStand(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Stand expected = StandTestUtils.createDefaultInstance();
        session.returns(expected).load(Stand.class, StandTestUtils.getDefaultIdentity());
        standDao.removeStand(StandTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}