package test.pack.data.greenvine.dao.test.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Type;
import test.pack.data.greenvine.entity.test.TypeTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TypeDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private TypeDaoImpl typeDao;
    
    @Test
    public void testLoadExisting() {
    
        Type expected = TypeTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Type.class, TypeTestUtils.getDefaultIdentity());
        Type actual = typeDao.loadType(TypeTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Type.class, TypeTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Type.class, TypeTestUtils.getDefaultIdentity());
        typeDao.loadType(TypeTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Type expected = TypeTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Type.class, TypeTestUtils.getDefaultIdentity());
        Type actual = typeDao.findType(TypeTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Type.class, TypeTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Type.class, TypeTestUtils.getDefaultIdentity());
        Type actual = typeDao.findType(TypeTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Type expected = TypeTestUtils.createDefaultInstance();
        typeDao.createType(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Type expected = TypeTestUtils.createDefaultInstance();
        typeDao.updateType(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Type expected = TypeTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Type.class, TypeTestUtils.getDefaultIdentity());
        typeDao.removeType(TypeTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}