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

import test.pack.data.greenvine.entity.test.Address;
import test.pack.data.greenvine.entity.test.AddressTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class AddressHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("AddressBeforeCreateDataSet.xml")
    @ExpectedDataSet("AddressAfterCreateDataSet.xml")
    public void testCreateAddress() throws Exception {
    
        // Create new entity
        Address create = new Address();
                    
        // Set identity
        create.setAddressId(Integer.valueOf(1));

        // Set natural identity
        create.setAddressNaturalIdentity(AddressTestUtils.getDefaultNaturalIdentity());
  
        // populate simple properties
        create.setPostCode("s");
        // Get Hibernate Session
        Session session = factory.getCurrentSession();

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("AddressBeforeUpdateDataSet.xml")
    @ExpectedDataSet("AddressAfterUpdateDataSet.xml")
    public void testUpdateAddress() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Address result = (Address)session.get(Address.class, AddressTestUtils.getDefaultIdentity());
        result.setPostCode("t");
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("AddressBeforeDeleteDataSet.xml")
    @ExpectedDataSet("AddressAfterDeleteDataSet.xml")
    public void testRemoveAddress() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Address result = (Address)session.get(Address.class, AddressTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("AddressFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllAddresss() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.test.Address");
        
        // Get results 
        List<Address> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("AddressFindDataSet.xml")
    public void testFindAddressByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Address result  = (Address)session.get(Address.class, AddressTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getPostCode());

    }
    
}