package test.pack.data.greenvine.entity.test;

import java.util.List;
import java.util.Date;

import org.junit.Assert;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.test.ParkingPermitPayment;
import test.pack.data.greenvine.entity.test.ParkingPermitPaymentTestUtils;
import test.pack.data.greenvine.entity.test.ParkingPermit;
import test.pack.data.greenvine.entity.test.ParkingPermitTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ParkingPermitPaymentHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("ParkingPermitPaymentBeforeCreateDataSet.xml")
    @ExpectedDataSet("ParkingPermitPaymentAfterCreateDataSet.xml")
    public void testCreateParkingPermitPayment() throws Exception {
    
        // Create new entity
        ParkingPermitPayment create = new ParkingPermitPayment();
  
        // populate simple properties
        create.setPaymentDate(new Date(1230771661000L));
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
                
        // populate dependencies
        ParkingPermit parkingPermit = (ParkingPermit)session.get(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        create.setParkingPermit(parkingPermit);

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("ParkingPermitPaymentBeforeUpdateDataSet.xml")
    @ExpectedDataSet("ParkingPermitPaymentAfterUpdateDataSet.xml")
    public void testUpdateParkingPermitPayment() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        ParkingPermitPayment result = (ParkingPermitPayment)session.get(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        result.setPaymentDate(new Date(1233540122000L));
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("ParkingPermitPaymentBeforeDeleteDataSet.xml")
    @ExpectedDataSet("ParkingPermitPaymentAfterDeleteDataSet.xml")
    public void testRemoveParkingPermitPayment() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        ParkingPermitPayment result = (ParkingPermitPayment)session.get(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("ParkingPermitPaymentFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllParkingPermitPayments() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.test.ParkingPermitPayment");
        
        // Get results 
        List<ParkingPermitPayment> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("ParkingPermitPaymentFindDataSet.xml")
    public void testFindParkingPermitPaymentByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        ParkingPermitPayment result  = (ParkingPermitPayment)session.get(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals(new Date(1230771661000L), result.getPaymentDate());

    }
    
}