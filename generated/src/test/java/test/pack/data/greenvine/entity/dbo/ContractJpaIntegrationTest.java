package test.pack.data.greenvine.entity.dbo;

import java.util.List;

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

import test.pack.data.greenvine.entity.dbo.Contract;
import test.pack.data.greenvine.entity.dbo.ContractTestUtils;
import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.dbo.EmployeeTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ContractJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("ContractBeforeCreateDataSet.xml")
    @ExpectedDataSet("ContractAfterCreateDataSet.xml")
    public void testCreateContract() throws Exception {
    
        // Create new entity
        Contract create = new Contract();

        // Populate simple properties
        create.setTerms("s");
                
        // Populate dependencies
        Employee employee = (Employee)entityManager.getReference(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        create.setEmployee(employee);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("ContractBeforeUpdateDataSet.xml")
    @ExpectedDataSet("ContractAfterUpdateDataSet.xml")
    public void testUpdateContract() throws Exception {
                    
        // Load entity and modify
        Contract result = (Contract)entityManager.find(Contract.class, ContractTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setTerms("t");

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("ContractBeforeDeleteDataSet.xml")
    @ExpectedDataSet("ContractAfterDeleteDataSet.xml")
    public void testRemoveContract() throws Exception {
                    
        // Delete
        Contract result = (Contract)entityManager.find(Contract.class, ContractTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("ContractFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllContract() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from dbo.Contract");
                    
        // Get results 
        List<Contract> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("ContractFindDataSet.xml")
    public void testFindContractByIdentity() throws Exception {
                    
        // Get object 
        Contract result  = (Contract)entityManager.find(Contract.class, ContractTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals("s", result.getTerms());

    }
    
}