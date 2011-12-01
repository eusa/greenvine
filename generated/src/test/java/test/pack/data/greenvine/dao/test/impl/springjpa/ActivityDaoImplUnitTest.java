package test.pack.data.greenvine.dao.test.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Activity;
import test.pack.data.greenvine.entity.test.ActivityTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ActivityDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private ActivityDaoImpl activityDao;
    
    @Test
    public void testLoadExisting() {
    
        Activity expected = ActivityTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Activity.class, ActivityTestUtils.getDefaultIdentity());
        Activity actual = activityDao.loadActivity(ActivityTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Activity.class, ActivityTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Activity.class, ActivityTestUtils.getDefaultIdentity());
        activityDao.loadActivity(ActivityTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Activity expected = ActivityTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Activity.class, ActivityTestUtils.getDefaultIdentity());
        Activity actual = activityDao.findActivity(ActivityTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Activity.class, ActivityTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Activity.class, ActivityTestUtils.getDefaultIdentity());
        Activity actual = activityDao.findActivity(ActivityTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Activity expected = ActivityTestUtils.createDefaultInstance();
        activityDao.createActivity(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Activity expected = ActivityTestUtils.createDefaultInstance();
        activityDao.updateActivity(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Activity expected = ActivityTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Activity.class, ActivityTestUtils.getDefaultIdentity());
        activityDao.removeActivity(ActivityTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}