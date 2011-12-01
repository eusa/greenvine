package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;
import java.util.Date;

import org.junit.Assert;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.test.ParkingPermitPayment;
import test.pack.data.greenvine.entity.test.ParkingPermitPaymentTestUtils;
import test.pack.data.greenvine.entity.test.ParkingPermit;
import test.pack.data.greenvine.entity.test.ParkingPermitTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ParkingPermitPaymentDaoImplIntegrationTest {
    
    @TestedObject
    private ParkingPermitPaymentDaoImpl parkingPermitPaymentDao = null;

    @HibernateSessionFactory
    @InjectInto(property = "sessionFactory")
    private SessionFactory sessionFactory;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ParkingPermitPaymentBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ParkingPermitPaymentAfterCreateDataSet.xml")
    public void testCreateParkingPermitPayment() throws Exception {
    
        // Create new entity
        ParkingPermitPayment create = new ParkingPermitPayment();
                 
        // Populate simple properties
        create.setPaymentDate(new Date(1230771661000L));

        // Populate dependencies
        Session session = sessionFactory.getCurrentSession();
        
        ParkingPermit parkingPermit = (ParkingPermit)session.load(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        create.setParkingPermit(parkingPermit);

        // Create in database                    
        parkingPermitPaymentDao.createParkingPermitPayment(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ParkingPermitPaymentBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ParkingPermitPaymentAfterUpdateDataSet.xml")
    public void testUpdateParkingPermitPayment() throws Exception {
        
        // Load entity and modify
        ParkingPermitPayment update = parkingPermitPaymentDao.loadParkingPermitPayment(ParkingPermitPaymentTestUtils.getDefaultIdentity());
        update.setPaymentDate(new Date(1233540122000L));

        // Update entity
        parkingPermitPaymentDao.updateParkingPermitPayment(update);
                    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ParkingPermitPaymentBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ParkingPermitPaymentAfterDeleteDataSet.xml")
    public void testRemoveParkingPermitPayment() throws Exception {

        // Delete by id
        parkingPermitPaymentDao.removeParkingPermitPayment(ParkingPermitPaymentTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ParkingPermitPaymentFindAllDataSet.xml")
    public void testFindAllParkingPermitPayments() throws Exception {
    
        List<ParkingPermitPayment> results = parkingPermitPaymentDao.findAllParkingPermitPayments();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ParkingPermitPaymentFindDataSet.xml")
    public void testLoadParkingPermitPaymentByIdentity() throws Exception {

        ParkingPermitPayment result = parkingPermitPaymentDao.loadParkingPermitPayment(ParkingPermitPaymentTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals(new Date(1230771661000L), result.getPaymentDate());

    }

}