package test.pack.data.greenvine.dao.test.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.ParkingPermit;
import test.pack.data.greenvine.entity.test.ParkingPermitTestUtils;

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
public class ParkingPermitDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private ParkingPermitDaoImpl parkingPermitDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        ParkingPermit expected = ParkingPermitTestUtils.createDefaultInstance();
        session.returns(expected).load(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        ParkingPermit actual = parkingPermitDao.loadParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());
        session.assertInvoked().load(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        parkingPermitDao.loadParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        ParkingPermit expected = ParkingPermitTestUtils.createDefaultInstance();
        session.returns(expected).get(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        ParkingPermit actual = parkingPermitDao.findParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());
        session.assertInvoked().get(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        ParkingPermit actual = parkingPermitDao.findParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        ParkingPermit expected = ParkingPermitTestUtils.createDefaultInstance();
        parkingPermitDao.createParkingPermit(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        ParkingPermit expected = ParkingPermitTestUtils.createDefaultInstance();
        parkingPermitDao.updateParkingPermit(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        ParkingPermit expected = ParkingPermitTestUtils.createDefaultInstance();
        session.returns(expected).load(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        parkingPermitDao.removeParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}