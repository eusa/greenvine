package test.pack.data.greenvine.entity.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import test.pack.data.greenvine.entity.test.Address;
import test.pack.data.greenvine.entity.test.AddressTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class AddressJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

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

        // Populate simple properties
        create.setPostCode("s");

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("AddressBeforeUpdateDataSet.xml")
    @ExpectedDataSet("AddressAfterUpdateDataSet.xml")
    public void testUpdateAddress() throws Exception {
                    
        // Load entity and modify
        Address result = (Address)entityManager.find(Address.class, AddressTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setPostCode("t");

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("AddressBeforeDeleteDataSet.xml")
    @ExpectedDataSet("AddressAfterDeleteDataSet.xml")
    public void testRemoveAddress() throws Exception {
                    
        // Delete
        Address result = (Address)entityManager.find(Address.class, AddressTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("AddressFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllAddress() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from test.Address");
                    
        // Get results 
        List<Address> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("AddressFindDataSet.xml")
    public void testFindAddressByIdentity() throws Exception {
                    
        // Get object 
        Address result  = (Address)entityManager.find(Address.class, AddressTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals("s", result.getPostCode());

    }
    
}