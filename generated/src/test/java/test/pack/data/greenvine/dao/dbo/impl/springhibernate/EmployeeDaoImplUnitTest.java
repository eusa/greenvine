package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.dbo.EmployeeTestUtils;

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
public class EmployeeDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private EmployeeDaoImpl employeeDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Employee expected = EmployeeTestUtils.createDefaultInstance();
        session.returns(expected).load(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        Employee actual = employeeDao.loadEmployee(EmployeeTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        employeeDao.loadEmployee(EmployeeTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Employee expected = EmployeeTestUtils.createDefaultInstance();
        session.returns(expected).get(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        Employee actual = employeeDao.findEmployee(EmployeeTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        Employee actual = employeeDao.findEmployee(EmployeeTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Employee expected = EmployeeTestUtils.createDefaultInstance();
        employeeDao.createEmployee(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Employee expected = EmployeeTestUtils.createDefaultInstance();
        employeeDao.updateEmployee(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Employee expected = EmployeeTestUtils.createDefaultInstance();
        session.returns(expected).load(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        employeeDao.removeEmployee(EmployeeTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}