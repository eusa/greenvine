package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;
import java.math.BigDecimal;
import test.pack.data.greenvine.entity.test.ParkingPermitIdentity;

import org.junit.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import test.pack.data.greenvine.entity.test.ParkingPermit;
import test.pack.data.greenvine.entity.test.ParkingPermitTestUtils;
import test.pack.data.greenvine.entity.test.Vehicle;
import test.pack.data.greenvine.entity.test.VehicleTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ParkingPermitDaoImplIntegrationTest {
    
    @TestedObject
    private ParkingPermitDaoImpl parkingPermitDao = null;

    @PersistenceContext
    @InjectInto(property="entityManager")
    private EntityManager entityManager;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ParkingPermitBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ParkingPermitAfterCreateDataSet.xml")
    public void testCreateParkingPermit() throws Exception {
    
        // Create new entity
        ParkingPermit create = new ParkingPermit();

        // Set identity
        ParkingPermitIdentity parkingPermitIdentity = new ParkingPermitIdentity();
        ParkingPermitTestUtils.populateAssignedIdentityFields(parkingPermitIdentity);
        create.setParkingPermitIdentity(parkingPermitIdentity);
                 
        // Populate simple properties
        create.setValue(new BigDecimal("100.1"));

        // Populate dependencies
        Vehicle vehicle = (Vehicle)entityManager.getReference(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        create.setVehicle(vehicle);

        // Create in database                    
        parkingPermitDao.createParkingPermit(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ParkingPermitBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ParkingPermitAfterUpdateDataSet.xml")
    public void testUpdateParkingPermit() throws Exception {
        
         // Load entity and modify
        ParkingPermit update = parkingPermitDao.loadParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());
        update.setValue(new BigDecimal("200.2"));

        // Update entity
        parkingPermitDao.updateParkingPermit(update);

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ParkingPermitBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ParkingPermitAfterDeleteDataSet.xml")
    public void testRemoveParkingPermit() throws Exception {

        // Delete by id
        parkingPermitDao.removeParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ParkingPermitFindAllDataSet.xml")
    public void testFindAllParkingPermits() throws Exception {
    
        List<ParkingPermit> results = parkingPermitDao.findAllParkingPermits();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ParkingPermitFindDataSet.xml")
    public void testLoadParkingPermitByIdentity() throws Exception {
    
        ParkingPermit result = parkingPermitDao.loadParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals(new BigDecimal("100.1"), result.getValue());

    }

}