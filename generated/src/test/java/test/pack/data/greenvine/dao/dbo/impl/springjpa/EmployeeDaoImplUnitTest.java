package test.pack.data.greenvine.dao.dbo.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.dbo.EmployeeTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class EmployeeDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private EmployeeDaoImpl employeeDao;
    
    @Test
    public void testLoadExisting() {
    
        Employee expected = EmployeeTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        Employee actual = employeeDao.loadEmployee(EmployeeTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        employeeDao.loadEmployee(EmployeeTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Employee expected = EmployeeTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        Employee actual = employeeDao.findEmployee(EmployeeTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        Employee actual = employeeDao.findEmployee(EmployeeTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Employee expected = EmployeeTestUtils.createDefaultInstance();
        employeeDao.createEmployee(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Employee expected = EmployeeTestUtils.createDefaultInstance();
        employeeDao.updateEmployee(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Employee expected = EmployeeTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        employeeDao.removeEmployee(EmployeeTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}