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

import test.pack.data.greenvine.entity.dbo.Umbrella;
import test.pack.data.greenvine.entity.dbo.UmbrellaTestUtils;
import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.dbo.EmployeeTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class UmbrellaHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("UmbrellaBeforeCreateDataSet.xml")
    @ExpectedDataSet("UmbrellaAfterCreateDataSet.xml")
    public void testCreateUmbrella() throws Exception {
    
        // Create new entity
        Umbrella create = new Umbrella();
                    
        // Set identity
        create.setUmbrellaId(Integer.valueOf(1));
  
        // populate simple properties
        create.setColour("s");
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
                
        // populate dependencies
        Employee employee = (Employee)session.get(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        create.setEmployee(employee);

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("UmbrellaBeforeUpdateDataSet.xml")
    @ExpectedDataSet("UmbrellaAfterUpdateDataSet.xml")
    public void testUpdateUmbrella() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Umbrella result = (Umbrella)session.get(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        result.setColour("t");
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("UmbrellaBeforeDeleteDataSet.xml")
    @ExpectedDataSet("UmbrellaAfterDeleteDataSet.xml")
    public void testRemoveUmbrella() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Umbrella result = (Umbrella)session.get(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("UmbrellaFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllUmbrellas() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.dbo.Umbrella");
        
        // Get results 
        List<Umbrella> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("UmbrellaFindDataSet.xml")
    public void testFindUmbrellaByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Umbrella result  = (Umbrella)session.get(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getColour());

    }
    
}