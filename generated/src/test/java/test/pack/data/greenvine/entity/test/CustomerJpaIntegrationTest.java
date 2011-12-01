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

import test.pack.data.greenvine.entity.test.Customer;
import test.pack.data.greenvine.entity.test.CustomerTestUtils;
import test.pack.data.greenvine.entity.test.Person;
import test.pack.data.greenvine.entity.test.PersonTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class CustomerJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("CustomerBeforeCreateDataSet.xml")
    @ExpectedDataSet("CustomerAfterCreateDataSet.xml")
    public void testCreateCustomer() throws Exception {
    
        // Create new entity
        Customer create = new Customer();

        // Populate simple properties
        create.setLoyaltyPoints(Integer.valueOf(1));
                
        // Populate dependencies
        Person person = (Person)entityManager.getReference(Person.class, PersonTestUtils.getDefaultIdentity());
        create.setPerson(person);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("CustomerBeforeUpdateDataSet.xml")
    @ExpectedDataSet("CustomerAfterUpdateDataSet.xml")
    public void testUpdateCustomer() throws Exception {
                    
        // Load entity and modify
        Customer result = (Customer)entityManager.find(Customer.class, CustomerTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setLoyaltyPoints(Integer.valueOf(2));

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("CustomerBeforeDeleteDataSet.xml")
    @ExpectedDataSet("CustomerAfterDeleteDataSet.xml")
    public void testRemoveCustomer() throws Exception {
                    
        // Delete
        Customer result = (Customer)entityManager.find(Customer.class, CustomerTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("CustomerFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllCustomer() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from test.Customer");
                    
        // Get results 
        List<Customer> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("CustomerFindDataSet.xml")
    public void testFindCustomerByIdentity() throws Exception {
                    
        // Get object 
        Customer result  = (Customer)entityManager.find(Customer.class, CustomerTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals(Integer.valueOf(1), result.getLoyaltyPoints());

    }
    
}