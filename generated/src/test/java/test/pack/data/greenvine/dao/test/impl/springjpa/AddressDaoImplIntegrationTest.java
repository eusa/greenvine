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

import test.pack.data.greenvine.entity.test.Address;
import test.pack.data.greenvine.entity.test.AddressTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class AddressDaoImplIntegrationTest {
    
    @TestedObject
    private AddressDaoImpl addressDao = null;

    @SuppressWarnings("unused")
    @PersistenceContext
    @InjectInto(property="entityManager")
    private EntityManager entityManager;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/AddressBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/AddressAfterCreateDataSet.xml")
    public void testCreateAddress() throws Exception {
    
        // Create new entity
        Address create = new Address();
                    
        // Set identity
        create.setAddressId(Integer.valueOf(1));

        // Set natural identity
        create.setAddressNaturalIdentity(AddressTestUtils.getDefaultNaturalIdentity());
                 
        // Populate simple properties
        create.setPostCode("s");

        // Create in database                    
        addressDao.createAddress(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/AddressBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/AddressAfterUpdateDataSet.xml")
    public void testUpdateAddress() throws Exception {
        
         // Load entity and modify
        Address update = addressDao.loadAddress(AddressTestUtils.getDefaultIdentity());
        update.setPostCode("t");

        // Update entity
        addressDao.updateAddress(update);

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/AddressBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/AddressAfterDeleteDataSet.xml")
    public void testRemoveAddress() throws Exception {

        // Delete by id
        addressDao.removeAddress(AddressTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/AddressFindAllDataSet.xml")
    public void testFindAllAddresss() throws Exception {
    
        List<Address> results = addressDao.findAllAddresss();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/AddressFindDataSet.xml")
    public void testLoadAddressByIdentity() throws Exception {
    
        Address result = addressDao.loadAddress(AddressTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getPostCode());

    }

}