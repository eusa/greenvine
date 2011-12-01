package test.pack.data.greenvine.dao.test.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.ParkingPermit;
import test.pack.data.greenvine.entity.test.ParkingPermitTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ParkingPermitDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private ParkingPermitDaoImpl parkingPermitDao;
    
    @Test
    public void testLoadExisting() {
    
        ParkingPermit expected = ParkingPermitTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        ParkingPermit actual = parkingPermitDao.loadParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        parkingPermitDao.loadParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        ParkingPermit expected = ParkingPermitTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        ParkingPermit actual = parkingPermitDao.findParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        ParkingPermit actual = parkingPermitDao.findParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        ParkingPermit expected = ParkingPermitTestUtils.createDefaultInstance();
        parkingPermitDao.createParkingPermit(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        ParkingPermit expected = ParkingPermitTestUtils.createDefaultInstance();
        parkingPermitDao.updateParkingPermit(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        ParkingPermit expected = ParkingPermitTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        parkingPermitDao.removeParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}