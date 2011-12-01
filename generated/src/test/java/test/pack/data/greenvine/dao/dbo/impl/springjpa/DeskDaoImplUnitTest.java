package test.pack.data.greenvine.dao.dbo.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.dbo.Desk;
import test.pack.data.greenvine.entity.dbo.DeskTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class DeskDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private DeskDaoImpl deskDao;
    
    @Test
    public void testLoadExisting() {
    
        Desk expected = DeskTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Desk.class, DeskTestUtils.getDefaultIdentity());
        Desk actual = deskDao.loadDesk(DeskTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Desk.class, DeskTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Desk.class, DeskTestUtils.getDefaultIdentity());
        deskDao.loadDesk(DeskTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Desk expected = DeskTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Desk.class, DeskTestUtils.getDefaultIdentity());
        Desk actual = deskDao.findDesk(DeskTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Desk.class, DeskTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Desk.class, DeskTestUtils.getDefaultIdentity());
        Desk actual = deskDao.findDesk(DeskTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Desk expected = DeskTestUtils.createDefaultInstance();
        deskDao.createDesk(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Desk expected = DeskTestUtils.createDefaultInstance();
        deskDao.updateDesk(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Desk expected = DeskTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Desk.class, DeskTestUtils.getDefaultIdentity());
        deskDao.removeDesk(DeskTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}