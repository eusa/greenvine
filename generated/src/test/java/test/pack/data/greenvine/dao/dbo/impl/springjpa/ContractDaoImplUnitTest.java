package test.pack.data.greenvine.dao.dbo.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.dbo.Contract;
import test.pack.data.greenvine.entity.dbo.ContractTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ContractDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private ContractDaoImpl contractDao;
    
    @Test
    public void testLoadExisting() {
    
        Contract expected = ContractTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Contract.class, ContractTestUtils.getDefaultIdentity());
        Contract actual = contractDao.loadContract(ContractTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Contract.class, ContractTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Contract.class, ContractTestUtils.getDefaultIdentity());
        contractDao.loadContract(ContractTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Contract expected = ContractTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Contract.class, ContractTestUtils.getDefaultIdentity());
        Contract actual = contractDao.findContract(ContractTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Contract.class, ContractTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Contract.class, ContractTestUtils.getDefaultIdentity());
        Contract actual = contractDao.findContract(ContractTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Contract expected = ContractTestUtils.createDefaultInstance();
        contractDao.createContract(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Contract expected = ContractTestUtils.createDefaultInstance();
        contractDao.updateContract(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Contract expected = ContractTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Contract.class, ContractTestUtils.getDefaultIdentity());
        contractDao.removeContract(ContractTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}