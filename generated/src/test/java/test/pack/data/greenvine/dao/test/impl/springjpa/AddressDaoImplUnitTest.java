package test.pack.data.greenvine.dao.test.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Address;
import test.pack.data.greenvine.entity.test.AddressTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class AddressDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private AddressDaoImpl addressDao;
    
    @Test
    public void testLoadExisting() {
    
        Address expected = AddressTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Address.class, AddressTestUtils.getDefaultIdentity());
        Address actual = addressDao.loadAddress(AddressTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Address.class, AddressTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Address.class, AddressTestUtils.getDefaultIdentity());
        addressDao.loadAddress(AddressTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Address expected = AddressTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Address.class, AddressTestUtils.getDefaultIdentity());
        Address actual = addressDao.findAddress(AddressTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Address.class, AddressTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Address.class, AddressTestUtils.getDefaultIdentity());
        Address actual = addressDao.findAddress(AddressTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Address expected = AddressTestUtils.createDefaultInstance();
        addressDao.createAddress(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Address expected = AddressTestUtils.createDefaultInstance();
        addressDao.updateAddress(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Address expected = AddressTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Address.class, AddressTestUtils.getDefaultIdentity());
        addressDao.removeAddress(AddressTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}