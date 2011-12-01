package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

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

import test.pack.data.greenvine.entity.test.Customer;
import test.pack.data.greenvine.entity.test.CustomerTestUtils;
import test.pack.data.greenvine.entity.test.Person;
import test.pack.data.greenvine.entity.test.PersonTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class CustomerDaoImplIntegrationTest {
    
    @TestedObject
    private CustomerDaoImpl customerDao = null;

    @HibernateSessionFactory
    @InjectInto(property = "sessionFactory")
    private SessionFactory sessionFactory;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/CustomerBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/CustomerAfterCreateDataSet.xml")
    public void testCreateCustomer() throws Exception {
    
        // Create new entity
        Customer create = new Customer();
                 
        // Populate simple properties
        create.setLoyaltyPoints(Integer.valueOf(1));

        // Populate dependencies
        Session session = sessionFactory.getCurrentSession();
        
        Person person = (Person)session.load(Person.class, PersonTestUtils.getDefaultIdentity());
        create.setPerson(person);

        // Create in database                    
        customerDao.createCustomer(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/CustomerBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/CustomerAfterUpdateDataSet.xml")
    public void testUpdateCustomer() throws Exception {
        
        // Load entity and modify
        Customer update = customerDao.loadCustomer(CustomerTestUtils.getDefaultIdentity());
        update.setLoyaltyPoints(Integer.valueOf(2));

        // Update entity
        customerDao.updateCustomer(update);
                    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/CustomerBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/CustomerAfterDeleteDataSet.xml")
    public void testRemoveCustomer() throws Exception {

        // Delete by id
        customerDao.removeCustomer(CustomerTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/CustomerFindAllDataSet.xml")
    public void testFindAllCustomers() throws Exception {
    
        List<Customer> results = customerDao.findAllCustomers();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/CustomerFindDataSet.xml")
    public void testLoadCustomerByIdentity() throws Exception {

        Customer result = customerDao.loadCustomer(CustomerTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals(Integer.valueOf(1), result.getLoyaltyPoints());

    }

}