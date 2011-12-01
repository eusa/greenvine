package test.pack.data.greenvine.dao.test.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.ParkingPermitPayment;
import test.pack.data.greenvine.entity.test.ParkingPermitPaymentTestUtils;

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
public class ParkingPermitPaymentDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private ParkingPermitPaymentDaoImpl parkingPermitPaymentDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        ParkingPermitPayment expected = ParkingPermitPaymentTestUtils.createDefaultInstance();
        session.returns(expected).load(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        ParkingPermitPayment actual = parkingPermitPaymentDao.loadParkingPermitPayment(ParkingPermitPaymentTestUtils.getDefaultIdentity());
        session.assertInvoked().load(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        parkingPermitPaymentDao.loadParkingPermitPayment(ParkingPermitPaymentTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        ParkingPermitPayment expected = ParkingPermitPaymentTestUtils.createDefaultInstance();
        session.returns(expected).get(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        ParkingPermitPayment actual = parkingPermitPaymentDao.findParkingPermitPayment(ParkingPermitPaymentTestUtils.getDefaultIdentity());
        session.assertInvoked().get(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        ParkingPermitPayment actual = parkingPermitPaymentDao.findParkingPermitPayment(ParkingPermitPaymentTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        ParkingPermitPayment expected = ParkingPermitPaymentTestUtils.createDefaultInstance();
        parkingPermitPaymentDao.createParkingPermitPayment(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        ParkingPermitPayment expected = ParkingPermitPaymentTestUtils.createDefaultInstance();
        parkingPermitPaymentDao.updateParkingPermitPayment(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        ParkingPermitPayment expected = ParkingPermitPaymentTestUtils.createDefaultInstance();
        session.returns(expected).load(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        parkingPermitPaymentDao.removeParkingPermitPayment(ParkingPermitPaymentTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}