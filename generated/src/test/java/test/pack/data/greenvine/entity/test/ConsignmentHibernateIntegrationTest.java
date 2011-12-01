package test.pack.data.greenvine.entity.test;

import java.util.List;

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

import test.pack.data.greenvine.entity.test.Consignment;
import test.pack.data.greenvine.entity.test.ConsignmentTestUtils;
import test.pack.data.greenvine.entity.test.Address;
import test.pack.data.greenvine.entity.test.AddressTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ConsignmentHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("ConsignmentBeforeCreateDataSet.xml")
    @ExpectedDataSet("ConsignmentAfterCreateDataSet.xml")
    public void testCreateConsignment() throws Exception {
    
        // Create new entity
        Consignment create = new Consignment();
                    
        // Set identity
        create.setConsignmentId(Integer.valueOf(1));

        // Set natural identity
        create.setConsignmentNaturalIdentity(ConsignmentTestUtils.getDefaultNaturalIdentity());
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
                
        // populate dependencies
        Address address = (Address)session.get(Address.class, AddressTestUtils.getDefaultIdentity());
        create.setAddress(address);

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("ConsignmentBeforeUpdateDataSet.xml")
    @ExpectedDataSet("ConsignmentAfterUpdateDataSet.xml")
    public void testUpdateConsignment() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Consignment result = (Consignment)session.get(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("ConsignmentBeforeDeleteDataSet.xml")
    @ExpectedDataSet("ConsignmentAfterDeleteDataSet.xml")
    public void testRemoveConsignment() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Consignment result = (Consignment)session.get(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("ConsignmentFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllConsignments() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.test.Consignment");
        
        // Get results 
        List<Consignment> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("ConsignmentFindDataSet.xml")
    public void testFindConsignmentByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Consignment result  = (Consignment)session.get(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);

    }
    
}