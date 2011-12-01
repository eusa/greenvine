package test.pack.data.greenvine.dao.dbo.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.dbo.Umbrella;
import test.pack.data.greenvine.entity.dbo.UmbrellaTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class UmbrellaDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private UmbrellaDaoImpl umbrellaDao;
    
    @Test
    public void testLoadExisting() {
    
        Umbrella expected = UmbrellaTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        Umbrella actual = umbrellaDao.loadUmbrella(UmbrellaTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        umbrellaDao.loadUmbrella(UmbrellaTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Umbrella expected = UmbrellaTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        Umbrella actual = umbrellaDao.findUmbrella(UmbrellaTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        Umbrella actual = umbrellaDao.findUmbrella(UmbrellaTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Umbrella expected = UmbrellaTestUtils.createDefaultInstance();
        umbrellaDao.createUmbrella(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Umbrella expected = UmbrellaTestUtils.createDefaultInstance();
        umbrellaDao.updateUmbrella(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Umbrella expected = UmbrellaTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        umbrellaDao.removeUmbrella(UmbrellaTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}