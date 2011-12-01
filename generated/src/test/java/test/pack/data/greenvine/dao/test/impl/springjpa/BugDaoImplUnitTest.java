package test.pack.data.greenvine.dao.test.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Bug;
import test.pack.data.greenvine.entity.test.BugTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class BugDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private BugDaoImpl bugDao;
    
    @Test
    public void testLoadExisting() {
    
        Bug expected = BugTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Bug.class, BugTestUtils.getDefaultIdentity());
        Bug actual = bugDao.loadBug(BugTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Bug.class, BugTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Bug.class, BugTestUtils.getDefaultIdentity());
        bugDao.loadBug(BugTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Bug expected = BugTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Bug.class, BugTestUtils.getDefaultIdentity());
        Bug actual = bugDao.findBug(BugTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Bug.class, BugTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Bug.class, BugTestUtils.getDefaultIdentity());
        Bug actual = bugDao.findBug(BugTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Bug expected = BugTestUtils.createDefaultInstance();
        bugDao.createBug(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Bug expected = BugTestUtils.createDefaultInstance();
        bugDao.updateBug(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Bug expected = BugTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Bug.class, BugTestUtils.getDefaultIdentity());
        bugDao.removeBug(BugTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}