package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.dbo.Contract;
import test.pack.data.greenvine.entity.dbo.ContractTestUtils;

import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ContractDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private ContractDaoImpl contractDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Contract expected = ContractTestUtils.createDefaultInstance();
        session.returns(expected).load(Contract.class, ContractTestUtils.getDefaultIdentity());
        Contract actual = contractDao.loadContract(ContractTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Contract.class, ContractTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Contract.class, ContractTestUtils.getDefaultIdentity());
        contractDao.loadContract(ContractTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Contract expected = ContractTestUtils.createDefaultInstance();
        session.returns(expected).get(Contract.class, ContractTestUtils.getDefaultIdentity());
        Contract actual = contractDao.findContract(ContractTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Contract.class, ContractTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Contract.class, ContractTestUtils.getDefaultIdentity());
        Contract actual = contractDao.findContract(ContractTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Contract expected = ContractTestUtils.createDefaultInstance();
        contractDao.createContract(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Contract expected = ContractTestUtils.createDefaultInstance();
        contractDao.updateContract(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Contract expected = ContractTestUtils.createDefaultInstance();
        session.returns(expected).load(Contract.class, ContractTestUtils.getDefaultIdentity());
        contractDao.removeContract(ContractTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}