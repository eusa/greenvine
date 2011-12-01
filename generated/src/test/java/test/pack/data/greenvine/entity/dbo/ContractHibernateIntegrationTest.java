package test.pack.data.greenvine.entity.dbo;

import java.util.List;

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

import test.pack.data.greenvine.entity.dbo.Contract;
import test.pack.data.greenvine.entity.dbo.ContractTestUtils;
import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.dbo.EmployeeTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ContractHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("ContractBeforeCreateDataSet.xml")
    @ExpectedDataSet("ContractAfterCreateDataSet.xml")
    public void testCreateContract() throws Exception {
    
        // Create new entity
        Contract create = new Contract();
  
        // populate simple properties
        create.setTerms("s");
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
                
        // populate dependencies
        Employee employee = (Employee)session.get(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        create.setEmployee(employee);

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("ContractBeforeUpdateDataSet.xml")
    @ExpectedDataSet("ContractAfterUpdateDataSet.xml")
    public void testUpdateContract() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Contract result = (Contract)session.get(Contract.class, ContractTestUtils.getDefaultIdentity());
        result.setTerms("t");
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("ContractBeforeDeleteDataSet.xml")
    @ExpectedDataSet("ContractAfterDeleteDataSet.xml")
    public void testRemoveContract() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Contract result = (Contract)session.get(Contract.class, ContractTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("ContractFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllContracts() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.dbo.Contract");
        
        // Get results 
        List<Contract> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("ContractFindDataSet.xml")
    public void testFindContractByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Contract result  = (Contract)session.get(Contract.class, ContractTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getTerms());

    }
    
}