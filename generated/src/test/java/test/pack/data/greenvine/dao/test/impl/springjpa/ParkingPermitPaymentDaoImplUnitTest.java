package test.pack.data.greenvine.dao.test.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.ParkingPermitPayment;
import test.pack.data.greenvine.entity.test.ParkingPermitPaymentTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ParkingPermitPaymentDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private ParkingPermitPaymentDaoImpl parkingPermitPaymentDao;
    
    @Test
    public void testLoadExisting() {
    
        ParkingPermitPayment expected = ParkingPermitPaymentTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        ParkingPermitPayment actual = parkingPermitPaymentDao.loadParkingPermitPayment(ParkingPermitPaymentTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        parkingPermitPaymentDao.loadParkingPermitPayment(ParkingPermitPaymentTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        ParkingPermitPayment expected = ParkingPermitPaymentTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        ParkingPermitPayment actual = parkingPermitPaymentDao.findParkingPermitPayment(ParkingPermitPaymentTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        ParkingPermitPayment actual = parkingPermitPaymentDao.findParkingPermitPayment(ParkingPermitPaymentTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        ParkingPermitPayment expected = ParkingPermitPaymentTestUtils.createDefaultInstance();
        parkingPermitPaymentDao.createParkingPermitPayment(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        ParkingPermitPayment expected = ParkingPermitPaymentTestUtils.createDefaultInstance();
        parkingPermitPaymentDao.updateParkingPermitPayment(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        ParkingPermitPayment expected = ParkingPermitPaymentTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        parkingPermitPaymentDao.removeParkingPermitPayment(ParkingPermitPaymentTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}