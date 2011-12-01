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

import test.pack.data.greenvine.entity.test.Customer;
import test.pack.data.greenvine.entity.test.CustomerTestUtils;
import test.pack.data.greenvine.entity.test.Person;
import test.pack.data.greenvine.entity.test.PersonTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class CustomerHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("CustomerBeforeCreateDataSet.xml")
    @ExpectedDataSet("CustomerAfterCreateDataSet.xml")
    public void testCreateCustomer() throws Exception {
    
        // Create new entity
        Customer create = new Customer();
  
        // populate simple properties
        create.setLoyaltyPoints(Integer.valueOf(1));
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
                
        // populate dependencies
        Person person = (Person)session.get(Person.class, PersonTestUtils.getDefaultIdentity());
        create.setPerson(person);

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("CustomerBeforeUpdateDataSet.xml")
    @ExpectedDataSet("CustomerAfterUpdateDataSet.xml")
    public void testUpdateCustomer() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Customer result = (Customer)session.get(Customer.class, CustomerTestUtils.getDefaultIdentity());
        result.setLoyaltyPoints(Integer.valueOf(2));
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("CustomerBeforeDeleteDataSet.xml")
    @ExpectedDataSet("CustomerAfterDeleteDataSet.xml")
    public void testRemoveCustomer() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Customer result = (Customer)session.get(Customer.class, CustomerTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("CustomerFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllCustomers() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.test.Customer");
        
        // Get results 
        List<Customer> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("CustomerFindDataSet.xml")
    public void testFindCustomerByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Customer result  = (Customer)session.get(Customer.class, CustomerTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals(Integer.valueOf(1), result.getLoyaltyPoints());

    }
    
}