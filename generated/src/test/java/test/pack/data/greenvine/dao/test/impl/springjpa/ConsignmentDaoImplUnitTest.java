package test.pack.data.greenvine.dao.test.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Consignment;
import test.pack.data.greenvine.entity.test.ConsignmentTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ConsignmentDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private ConsignmentDaoImpl consignmentDao;
    
    @Test
    public void testLoadExisting() {
    
        Consignment expected = ConsignmentTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        Consignment actual = consignmentDao.loadConsignment(ConsignmentTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        consignmentDao.loadConsignment(ConsignmentTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Consignment expected = ConsignmentTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        Consignment actual = consignmentDao.findConsignment(ConsignmentTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        Consignment actual = consignmentDao.findConsignment(ConsignmentTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Consignment expected = ConsignmentTestUtils.createDefaultInstance();
        consignmentDao.createConsignment(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Consignment expected = ConsignmentTestUtils.createDefaultInstance();
        consignmentDao.updateConsignment(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Consignment expected = ConsignmentTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        consignmentDao.removeConsignment(ConsignmentTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}