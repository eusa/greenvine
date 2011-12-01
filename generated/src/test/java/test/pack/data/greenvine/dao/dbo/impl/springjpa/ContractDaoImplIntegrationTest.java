package test.pack.data.greenvine.dao.dbo.impl.springjpa;

import java.util.List;

import org.junit.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import test.pack.data.greenvine.entity.dbo.Contract;
import test.pack.data.greenvine.entity.dbo.ContractTestUtils;
import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.dbo.EmployeeTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ContractDaoImplIntegrationTest {
    
    @TestedObject
    private ContractDaoImpl contractDao = null;

    @PersistenceContext
    @InjectInto(property="entityManager")
    private EntityManager entityManager;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/ContractBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/ContractAfterCreateDataSet.xml")
    public void testCreateContract() throws Exception {
    
        // Create new entity
        Contract create = new Contract();
                 
        // Populate simple properties
        create.setTerms("s");

        // Populate dependencies
        Employee employee = (Employee)entityManager.getReference(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        create.setEmployee(employee);

        // Create in database                    
        contractDao.createContract(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/ContractBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/ContractAfterUpdateDataSet.xml")
    public void testUpdateContract() throws Exception {
        
         // Load entity and modify
        Contract update = contractDao.loadContract(ContractTestUtils.getDefaultIdentity());
        update.setTerms("t");

        // Update entity
        contractDao.updateContract(update);

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/ContractBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/ContractAfterDeleteDataSet.xml")
    public void testRemoveContract() throws Exception {

        // Delete by id
        contractDao.removeContract(ContractTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/ContractFindAllDataSet.xml")
    public void testFindAllContracts() throws Exception {
    
        List<Contract> results = contractDao.findAllContracts();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/ContractFindDataSet.xml")
    public void testLoadContractByIdentity() throws Exception {
    
        Contract result = contractDao.loadContract(ContractTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getTerms());

    }

}