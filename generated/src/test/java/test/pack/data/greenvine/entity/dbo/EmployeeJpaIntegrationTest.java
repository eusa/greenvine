package test.pack.data.greenvine.entity.dbo;

import java.util.List;
import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.dbo.EmployeeTestUtils;
import test.pack.data.greenvine.entity.dbo.User;
import test.pack.data.greenvine.entity.dbo.UserTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class EmployeeJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

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

        // Populate simple properties
        create.setDailyWorkingHours(new BigDecimal("100.1"));
        create.setFirstName("s");
        create.setHourlyCost(new BigDecimal("100000000000000.0001"));
        create.setLastName("s");
                
        // Populate dependencies
        User user = (User)entityManager.getReference(User.class, UserTestUtils.getDefaultIdentity());
        create.setUser(user);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("EmployeeBeforeUpdateDataSet.xml")
    @ExpectedDataSet("EmployeeAfterUpdateDataSet.xml")
    public void testUpdateEmployee() throws Exception {
                    
        // Load entity and modify
        Employee result = (Employee)entityManager.find(Employee.class, EmployeeTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setDailyWorkingHours(new BigDecimal("200.2"));
        result.setFirstName("t");
        result.setHourlyCost(new BigDecimal("200000000000000.0002"));
        result.setLastName("t");

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("EmployeeBeforeDeleteDataSet.xml")
    @ExpectedDataSet("EmployeeAfterDeleteDataSet.xml")
    public void testRemoveEmployee() throws Exception {
                    
        // Delete
        Employee result = (Employee)entityManager.find(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("EmployeeFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllEmployee() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from dbo.Employee");
                    
        // Get results 
        List<Employee> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("EmployeeFindDataSet.xml")
    public void testFindEmployeeByIdentity() throws Exception {
                    
        // Get object 
        Employee result  = (Employee)entityManager.find(Employee.class, EmployeeTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals(new BigDecimal("100.1"), result.getDailyWorkingHours());
        Assert.assertEquals("s", result.getFirstName());
        Assert.assertEquals(new BigDecimal("100000000000000.0001"), result.getHourlyCost());
        Assert.assertEquals("s", result.getLastName());

    }
    
}