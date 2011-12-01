package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.test.Type;
import test.pack.data.greenvine.entity.test.TypeTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TypeDaoImplIntegrationTest {
    
    @TestedObject
    private TypeDaoImpl typeDao = null;

    @SuppressWarnings("unused")
    @HibernateSessionFactory
    @InjectInto(property = "sessionFactory")
    private SessionFactory sessionFactory;
    
    @Test
	@Ignore
    @DataSet("/test/pack/data/greenvine/entity/test/TypeBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/TypeAfterCreateDataSet.xml")
    public void testCreateType() throws Exception {
    
        // Create new entity
        Type create = new Type();
                    
        // Set identity
        create.setType6(Long.valueOf(1L));                
                 
        // Populate simple properties
        create.setType1(Integer.valueOf(1));
        create.setType10(new Date(61000L));
        create.setType12(new Date(1230768000000L));
        create.setType13(new Date(1230771661000L));
        create.setType14(new byte[]{116,101,115,116,49});
        create.setType16("s");
        create.setType17("s");
        create.setType18(new byte[]{116,101,115,116,49});
        create.setType19("s");
        create.setType2(Boolean.TRUE);
        create.setType20("s");
        create.setType3(Byte.valueOf("1"));
        create.setType4(Short.valueOf((short)1));
        create.setType5(Long.valueOf(1L));
        create.setType7(new BigDecimal("10000000.01"));
        create.setType8(Double.valueOf(10000000000000000.1));
        create.setType9(Float.valueOf(1000000.1F));

        // Create in database                    
        typeDao.createType(create);
    
    }
    
    @Test
	@Ignore
    @DataSet("/test/pack/data/greenvine/entity/test/TypeBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/TypeAfterUpdateDataSet.xml")
    public void testUpdateType() throws Exception {
        
        // Load entity and modify
        Type update = typeDao.loadType(TypeTestUtils.getDefaultIdentity());
        update.setType1(Integer.valueOf(2));
        update.setType10(new Date(3722000L));
        update.setType12(new Date(1233532800000L));
        update.setType13(new Date(1233540122000L));
        update.setType14(new byte[]{116,101,115,116,50});
        update.setType16("t");
        update.setType17("t");
        update.setType18(new byte[]{116,101,115,116,50});
        update.setType19("t");
        update.setType2(Boolean.FALSE);
        update.setType20("t");
        update.setType3(Byte.valueOf("2"));
        update.setType4(Short.valueOf((short)2));
        update.setType5(Long.valueOf(2L));
        update.setType7(new BigDecimal("20000000.02"));
        update.setType8(Double.valueOf(20000000000000000.2));
        update.setType9(Float.valueOf(2000000.2F));

        // Update entity
        typeDao.updateType(update);
                    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/TypeBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/TypeAfterDeleteDataSet.xml")
    public void testRemoveType() throws Exception {

        // Delete by id
        typeDao.removeType(TypeTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/TypeFindAllDataSet.xml")
    public void testFindAllTypes() throws Exception {
    
        List<Type> results = typeDao.findAllTypes();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/TypeFindDataSet.xml")
    public void testLoadTypeByIdentity() throws Exception {

        Type result = typeDao.loadType(TypeTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals(Integer.valueOf(1), result.getType1());
        Assert.assertEquals(new Date(61000L), result.getType10());
        Assert.assertEquals(new Date(1230768000000L), result.getType12());
        Assert.assertEquals(new Date(1230771661000L), result.getType13());
        Assert.assertArrayEquals(new byte[]{116,101,115,116,49}, result.getType14());
        Assert.assertEquals("s", result.getType16());
        Assert.assertEquals("s", result.getType17());
        Assert.assertArrayEquals(new byte[]{116,101,115,116,49}, result.getType18());
        Assert.assertEquals("s", result.getType19());
        Assert.assertEquals(Boolean.TRUE, result.getType2());
        Assert.assertEquals("s", result.getType20());
        Assert.assertEquals(Byte.valueOf("1"), result.getType3());
        Assert.assertEquals(Short.valueOf((short)1), result.getType4());
        Assert.assertEquals(Long.valueOf(1L), result.getType5());
        Assert.assertEquals(new BigDecimal("10000000.01"), result.getType7());
        Assert.assertEquals(Double.valueOf(10000000000000000.1), result.getType8());
        Assert.assertEquals(Float.valueOf(1000000.1F), result.getType9());

    }

}