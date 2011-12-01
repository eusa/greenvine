package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import java.util.List;
import java.math.BigDecimal;

import org.junit.Assert;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.dbo.EmployeeTestUtils;
import test.pack.data.greenvine.entity.dbo.User;
import test.pack.data.greenvine.entity.dbo.UserTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class EmployeeDaoImplIntegrationTest {
    
    @TestedObject
    private EmployeeDaoImpl employeeDao = null;

    @HibernateSessionFactory
    @InjectInto(property = "sessionFactory")
    private SessionFactory sessionFactory;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/EmployeeBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/EmployeeAfterCreateDataSet.xml")
    public void testCreateEmployee() throws Exception {
    
        // Create new entity
        Employee create = new Employee();
                    
        // Set identity
        create.setEmployeeId(Integer.valueOf(1));                

        // Set natural identity
        create.setEmail("s");
                 
        // Populate simple properties
        create.setDailyWorkingHours(new BigDecimal("100.1"));
        create.setFirstName("s");
        create.setHourlyCost(new BigDecimal("100000000000000.0001"));
        create.setLastName("s");

        // Populate dependencies
        Session session = sessionFactory.getCurrentSession();
        
        User user = (User)session.load(User.class, UserTestUtils.getDefaultIdentity());
        create.setUser(user);

        // Create in database                    
        employeeDao.createEmployee(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/EmployeeBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/EmployeeAfterUpdateDataSet.xml")
    public void testUpdateEmployee() throws Exception {
        
        // Load entity and modify
        Employee update = employeeDao.loadEmployee(EmployeeTestUtils.getDefaultIdentity());
        update.setDailyWorkingHours(new BigDecimal("200.2"));
        update.setFirstName("t");
        update.setHourlyCost(new BigDecimal("200000000000000.0002"));
        update.setLastName("t");

        // Update entity
        employeeDao.updateEmployee(update);
                    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/EmployeeBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/EmployeeAfterDeleteDataSet.xml")
    public void testRemoveEmployee() throws Exception {

        // Delete by id
        employeeDao.removeEmployee(EmployeeTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/EmployeeFindAllDataSet.xml")
    public void testFindAllEmployees() throws Exception {
    
        List<Employee> results = employeeDao.findAllEmployees();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/EmployeeFindDataSet.xml")
    public void testLoadEmployeeByIdentity() throws Exception {

        Employee result = employeeDao.loadEmployee(EmployeeTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals(new BigDecimal("100.1"), result.getDailyWorkingHours());
        Assert.assertEquals("s", result.getFirstName());
        Assert.assertEquals(new BigDecimal("100000000000000.0001"), result.getHourlyCost());
        Assert.assertEquals("s", result.getLastName());

    }

}