package test.pack.data.greenvine.dao.test.impl.springjpa;

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

import test.pack.data.greenvine.entity.test.Consignment;
import test.pack.data.greenvine.entity.test.ConsignmentTestUtils;
import test.pack.data.greenvine.entity.test.Address;
import test.pack.data.greenvine.entity.test.AddressTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ConsignmentDaoImplIntegrationTest {
    
    @TestedObject
    private ConsignmentDaoImpl consignmentDao = null;

    @PersistenceContext
    @InjectInto(property="entityManager")
    private EntityManager entityManager;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ConsignmentBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ConsignmentAfterCreateDataSet.xml")
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
        consignmentDao.createConsignment(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ConsignmentBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ConsignmentAfterUpdateDataSet.xml")
    public void testUpdateConsignment() throws Exception {
        
         // Load entity and modify
        Consignment update = consignmentDao.loadConsignment(ConsignmentTestUtils.getDefaultIdentity());

        // Update entity
        consignmentDao.updateConsignment(update);

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ConsignmentBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ConsignmentAfterDeleteDataSet.xml")
    public void testRemoveConsignment() throws Exception {

        // Delete by id
        consignmentDao.removeConsignment(ConsignmentTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ConsignmentFindAllDataSet.xml")
    public void testFindAllConsignments() throws Exception {
    
        List<Consignment> results = consignmentDao.findAllConsignments();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ConsignmentFindDataSet.xml")
    public void testLoadConsignmentByIdentity() throws Exception {
    
        Consignment result = consignmentDao.loadConsignment(ConsignmentTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);

    }

}