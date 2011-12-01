package test.pack.data.greenvine.dao.test.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Vehicle;
import test.pack.data.greenvine.entity.test.VehicleTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class VehicleDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private VehicleDaoImpl vehicleDao;
    
    @Test
    public void testLoadExisting() {
    
        Vehicle expected = VehicleTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        Vehicle actual = vehicleDao.loadVehicle(VehicleTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        vehicleDao.loadVehicle(VehicleTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Vehicle expected = VehicleTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        Vehicle actual = vehicleDao.findVehicle(VehicleTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        Vehicle actual = vehicleDao.findVehicle(VehicleTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Vehicle expected = VehicleTestUtils.createDefaultInstance();
        vehicleDao.createVehicle(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Vehicle expected = VehicleTestUtils.createDefaultInstance();
        vehicleDao.updateVehicle(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Vehicle expected = VehicleTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        vehicleDao.removeVehicle(VehicleTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}