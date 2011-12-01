package test.pack.data.greenvine.entity.test;

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

import test.pack.data.greenvine.entity.test.Consignment;
import test.pack.data.greenvine.entity.test.ConsignmentTestUtils;
import test.pack.data.greenvine.entity.test.Address;
import test.pack.data.greenvine.entity.test.AddressTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ConsignmentJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("ConsignmentBeforeCreateDataSet.xml")
    @ExpectedDataSet("ConsignmentAfterCreateDataSet.xml")
    public void testCreateConsignment() throws Exception {
    
        // Create new entity
        Consignment create = new Consignment();
                    
        // Set identity
        create.setConsignmentId(Integer.valueOf(1));

        // Set natural identity
        create.setConsignmentNaturalIdentity(ConsignmentTestUtils.getDefaultNaturalIdentity());
                
        // Populate dependencies
        Address address = (Address)entityManager.getReference(Address.class, AddressTestUtils.getDefaultIdentity());
        create.setAddress(address);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("ConsignmentBeforeUpdateDataSet.xml")
    @ExpectedDataSet("ConsignmentAfterUpdateDataSet.xml")
    public void testUpdateConsignment() throws Exception {
                    
        // Load entity and modify
        Consignment result = (Consignment)entityManager.find(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("ConsignmentBeforeDeleteDataSet.xml")
    @ExpectedDataSet("ConsignmentAfterDeleteDataSet.xml")
    public void testRemoveConsignment() throws Exception {
                    
        // Delete
        Consignment result = (Consignment)entityManager.find(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("ConsignmentFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllConsignment() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from test.Consignment");
                    
        // Get results 
        List<Consignment> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("ConsignmentFindDataSet.xml")
    public void testFindConsignmentByIdentity() throws Exception {
                    
        // Get object 
        Consignment result  = (Consignment)entityManager.find(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

    }
    
}