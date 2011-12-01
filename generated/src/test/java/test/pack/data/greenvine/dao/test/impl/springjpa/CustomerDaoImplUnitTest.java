package test.pack.data.greenvine.dao.test.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Customer;
import test.pack.data.greenvine.entity.test.CustomerTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class CustomerDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private CustomerDaoImpl customerDao;
    
    @Test
    public void testLoadExisting() {
    
        Customer expected = CustomerTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Customer.class, CustomerTestUtils.getDefaultIdentity());
        Customer actual = customerDao.loadCustomer(CustomerTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Customer.class, CustomerTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Customer.class, CustomerTestUtils.getDefaultIdentity());
        customerDao.loadCustomer(CustomerTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Customer expected = CustomerTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Customer.class, CustomerTestUtils.getDefaultIdentity());
        Customer actual = customerDao.findCustomer(CustomerTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Customer.class, CustomerTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Customer.class, CustomerTestUtils.getDefaultIdentity());
        Customer actual = customerDao.findCustomer(CustomerTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Customer expected = CustomerTestUtils.createDefaultInstance();
        customerDao.createCustomer(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Customer expected = CustomerTestUtils.createDefaultInstance();
        customerDao.updateCustomer(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Customer expected = CustomerTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Customer.class, CustomerTestUtils.getDefaultIdentity());
        customerDao.removeCustomer(CustomerTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}