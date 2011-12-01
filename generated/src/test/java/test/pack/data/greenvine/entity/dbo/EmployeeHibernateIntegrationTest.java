package test.pack.data.greenvine.entity.dbo;

import java.util.List;
import java.math.BigDecimal;

import org.junit.Assert;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.dbo.EmployeeTestUtils;
import test.pack.data.greenvine.entity.dbo.User;
import test.pack.data.greenvine.entity.dbo.UserTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class EmployeeHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("EmployeeBeforeCreateDataSet.xml")
    @ExpectedDataSet("EmployeeAfterCreateDataSet.xml")
    public void testCreateEmployee() throws Exception {
    
        // Create new entity
        Employee create = new Employee();
                    
        // Set identity
        create.setEmployeeId(Integer.valueOf(1));

        // Set natural identity
        create.setEmail("s");
  
        // populate simple properties
        create.setDailyWorkingHours(new BigDecimal("100.1"));
        create.setFirstName("s");
        create.setHourlyCost(new BigDecimal("100000000000000.0001"));
        create.setLastName("s");
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
                
        // populate dependencies
        User user = (User)session.get(User.class, UserTestUtils.getDefaultIdentity());
        create.setUser(user);

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("EmployeeBeforeUpdateDataSet.xml")
    @ExpectedDataSet("EmployeeAfterUpdateDataSet.xml")
    public void testUpdateEmployee() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Employee result = (Employee)session.get(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        result.setDailyWorkingHours(new BigDecimal("200.2"));
        result.setFirstName("t");
        result.setHourlyCost(new BigDecimal("200000000000000.0002"));
        result.setLastName("t");
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("EmployeeBeforeDeleteDataSet.xml")
    @ExpectedDataSet("EmployeeAfterDeleteDataSet.xml")
    public void testRemoveEmployee() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Employee result = (Employee)session.get(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("EmployeeFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllEmployees() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.dbo.Employee");
        
        // Get results 
        List<Employee> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("EmployeeFindDataSet.xml")
    public void testFindEmployeeByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Employee result  = (Employee)session.get(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals(new BigDecimal("100.1"), result.getDailyWorkingHours());
        Assert.assertEquals("s", result.getFirstName());
        Assert.assertEquals(new BigDecimal("100000000000000.0001"), result.getHourlyCost());
        Assert.assertEquals("s", result.getLastName());

    }
    
}