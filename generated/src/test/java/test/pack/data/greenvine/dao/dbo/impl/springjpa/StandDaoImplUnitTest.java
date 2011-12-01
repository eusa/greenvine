package test.pack.data.greenvine.dao.dbo.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.dbo.Stand;
import test.pack.data.greenvine.entity.dbo.StandTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class StandDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private StandDaoImpl standDao;
    
    @Test
    public void testLoadExisting() {
    
        Stand expected = StandTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Stand.class, StandTestUtils.getDefaultIdentity());
        Stand actual = standDao.loadStand(StandTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Stand.class, StandTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Stand.class, StandTestUtils.getDefaultIdentity());
        standDao.loadStand(StandTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Stand expected = StandTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Stand.class, StandTestUtils.getDefaultIdentity());
        Stand actual = standDao.findStand(StandTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Stand.class, StandTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Stand.class, StandTestUtils.getDefaultIdentity());
        Stand actual = standDao.findStand(StandTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Stand expected = StandTestUtils.createDefaultInstance();
        standDao.createStand(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Stand expected = StandTestUtils.createDefaultInstance();
        standDao.updateStand(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Stand expected = StandTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Stand.class, StandTestUtils.getDefaultIdentity());
        standDao.removeStand(StandTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}