package net.sourceforge.greenvine.model.reveng;

import java.io.IOException;
import java.sql.SQLException;

import junit.framework.Assert;
import net.sourceforge.greenvine.dbextractor.DatabaseExtractorException;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.impl.CatalogImpl;
import net.sourceforge.greenvine.model.api.impl.ModelImpl;
import net.sourceforge.greenvine.reveng.impl.RevengConfig;
import net.sourceforge.greenvine.reveng.impl.ReverseEngineerImpl;
import net.sourceforge.greenvine.reveng.testutils.ModelAssert;
import net.sourceforge.greenvine.reveng.testutils.TestDatabaseExtractor;

import org.junit.BeforeClass;
import org.junit.Test;

public class ReverseEngineerImplITCase {
    
	private static Database h2 = null;
	private static Database mysql = null;
	
	@BeforeClass
	public static void before() throws SQLException, ClassNotFoundException, DatabaseExtractorException, IOException {
		h2 = TestDatabaseExtractor.extractH2Database("../database-extractor/src/test/resources/test-schema-h2.sql");
		mysql = TestDatabaseExtractor.extractMySqlDatabase("../database-extractor/src/test/resources/test-schema-mysql.sql");
	}
       
    @Test
    public void testReverseEngineerH2() throws ModelException {
    	
        // Set up RevengConfig
        RevengConfig revengConfig = new RevengConfig();
        revengConfig.setModelName("model");
        revengConfig.setCreateNaturalIdentities(true);
        
        // Create ReverseEngineer
        ReverseEngineerImpl reveng = new ReverseEngineerImpl(revengConfig);
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(h2);
        
        // Validate model
        Assert.assertNotNull(model);
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        
        // Validate catalog
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertEquals("greenvine", catalog.getName().toString());
        Assert.assertEquals(23, catalog.getEntityCount());
        
        // Create ModelAssert object to test entities
        ModelAssert mAssert = new ModelAssert(catalog);
        
        // 1. Validate contract
        mAssert.assertEntityExists("dbo.contract");
        mAssert.assertOneToOneIdentity("dbo.contract", "employee", "dbo.employee");
        mAssert.assertSimpleProperty("dbo.contract", "terms", PropertyType.STRING);
        
        // 2. Validate desk
        mAssert.assertEntityExists("dbo.desk");
        mAssert.assertSimpleIdentity("dbo.desk", "deskId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("dbo.desk", "code", PropertyType.STRING);
        mAssert.assertOneToOneAssociation("dbo.desk", "employee", "dbo.employee");
        
        // 3. Validate employee
        mAssert.assertEntityExists("dbo.employee");
        mAssert.assertSimpleIdentity("dbo.employee", "employeeId", PropertyType.INTEGER);
        mAssert.assertSimpleNaturalIdentity("dbo.employee", "email", PropertyType.STRING);
        mAssert.assertSimpleProperty("dbo.employee", "firstName", PropertyType.STRING);
        mAssert.assertSimpleProperty("dbo.employee", "lastName", PropertyType.STRING);
        mAssert.assertSimpleProperty("dbo.employee", "hourlyCost", PropertyType.BIG_DECIMAL);
        mAssert.assertSimpleProperty("dbo.employee", "dailyWorkingHours", PropertyType.BIG_DECIMAL);
        mAssert.assertOneToOneAssociation("dbo.employee", "desk", "dbo.desk");
        mAssert.assertOneToOneParent("dbo.employee", "contract", "dbo.contract");
        mAssert.assertOneToOneChild("dbo.employee", "user", "dbo.user");
        mAssert.assertManyToOne("dbo.employee", "manager", "dbo.employee");
        mAssert.assertOneToMany("dbo.employee", "employees", "dbo.employee");
        mAssert.assertOneToMany("dbo.employee", "umbrellas", "dbo.umbrella");
        mAssert.assertManyToOneAssociation("dbo.employee", "employeeMentor", "dbo.employee"); 
        mAssert.assertOneToManyAssociation("dbo.employee", "employeeMentorees", "dbo.employee"); 
        
        // 4. Validate group
        mAssert.assertEntityExists("dbo.group");
        mAssert.assertSimpleIdentity("dbo.group", "groupId", PropertyType.INTEGER);
        mAssert.assertSimpleNaturalIdentity("dbo.group", "groupname", PropertyType.STRING);
        mAssert.assertManyToManyAssociation("dbo.group", "users", "dbo.user");
        
        // 5. Validate stand
        mAssert.assertEntityExists("dbo.stand");
        mAssert.assertSimpleIdentity("dbo.stand", "standId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("dbo.stand", "description", PropertyType.STRING);
        mAssert.assertOneToManyAssociation("dbo.stand", "umbrellas", "dbo.umbrella");
        
        // 6. Validate umbrella
        mAssert.assertEntityExists("dbo.umbrella");
        mAssert.assertSimpleIdentity("dbo.umbrella", "umbrellaId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("dbo.umbrella", "colour", PropertyType.STRING);
        mAssert.assertManyToOneAssociation("dbo.umbrella", "stand", "dbo.stand");
        
        // 7. Validate user
        mAssert.assertEntityExists("dbo.user");
        mAssert.assertSimpleIdentity("dbo.user", "userId", PropertyType.INTEGER);
        mAssert.assertSimpleNaturalIdentity("dbo.user", "username", PropertyType.STRING);
        mAssert.assertSimpleProperty("dbo.user", "password", PropertyType.STRING);
        mAssert.assertManyToManyAssociation("dbo.user", "groups", "dbo.group");
        
        // 8. Validate activity
        mAssert.assertEntityExists("testschema.activity");
        mAssert.assertSimpleIdentity("testschema.activity", "activityId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("testschema.activity", "description", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.activity", "hours", PropertyType.BIG_DECIMAL);
        mAssert.assertManyToOne("testschema.activity", "timesheet", "testschema.timesheet");
        
        // 9. Validate address
        mAssert.assertEntityExists("testschema.address");
        mAssert.assertSimpleIdentity("testschema.address", "addressId", PropertyType.INTEGER);
        mAssert.assertComponentNaturalIdentity("testschema.address", "houseNumber", "streetName");
        mAssert.assertSimpleProperty("testschema.address", "postCode", PropertyType.STRING);
        mAssert.assertOneToMany("testschema.address", "consignments", "testschema.consignment");
        
        // 10. Validate consignment
        mAssert.assertEntityExists("testschema.consignment");
        mAssert.assertSimpleIdentity("testschema.consignment", "consignmentId", PropertyType.INTEGER);
        mAssert.assertComponentNaturalIdentity("testschema.consignment", "customer", "consignmentDate");
        mAssert.assertManyToOne("testschema.consignment", "address", "testschema.address");
        
        // 11. Validate customer
        mAssert.assertEntityExists("testschema.customer");
        mAssert.assertOneToOneIdentity("testschema.customer", "person", "testschema.person");
        mAssert.assertSimpleProperty("testschema.customer", "loyaltyPoints", PropertyType.INTEGER);
        mAssert.assertOneToMany("testschema.customer", "consignments", "testschema.consignment");
        
        // 12. Validate parkingPermit
        mAssert.assertEntityExists("testschema.parkingPermit");
        mAssert.assertComponentIdentity("testschema.parkingPermit", "regNumber", "date"); 
        mAssert.assertSimpleProperty("testschema.parkingPermit", "value", PropertyType.BIG_DECIMAL);
        mAssert.assertManyToOne("testschema.parkingPermit", "vehicle", "testschema.vehicle");
        mAssert.assertOneToOneParent("testschema.parkingPermit", "parkingPermitPayment", "testschema.parkingPermitPayment");

        // 13. Validate parkingPermitPayment
        mAssert.assertEntityExists("testschema.parkingPermitPayment");
        mAssert.assertOneToOneIdentity("testschema.parkingPermitPayment", "parkingPermit", "testschema.parkingPermit");
        mAssert.assertSimpleProperty("testschema.parkingPermitPayment", "paymentDate", PropertyType.TIMESTAMP);

        // 14. Validate passport
        mAssert.assertEntityExists("testschema.passport");
        mAssert.assertSimpleIdentity("testschema.passport", "passportNr", PropertyType.STRING);
        mAssert.assertOneToOneNaturalIdentity("testschema.passport", "person", "testschema.person");
        mAssert.assertSimpleProperty("testschema.passport", "expiryDate", PropertyType.DATE);
        
        // 15. Validate person
        mAssert.assertEntityExists("testschema.person");
        mAssert.assertComponentIdentity("testschema.person", "firstName", "lastName");
        mAssert.assertOneToOneParent("testschema.person", "customer", "testschema.customer");
        mAssert.assertOneToOneParent("testschema.person", "passport", "testschema.passport");
        mAssert.assertSimpleProperty("testschema.person", "birthday", PropertyType.DATE);

        // 16. Validate profile
        mAssert.assertEntityExists("testschema.profile");
        mAssert.assertSimpleIdentity("testschema.profile", "profileId", PropertyType.INTEGER);
        mAssert.assertOneToOneNaturalIdentity("testschema.profile", "user", "testschema.user");
        mAssert.assertSimpleProperty("testschema.profile", "screenName", PropertyType.STRING);
        mAssert.assertManyToManyAssociation("testschema.profile", "friendRequesters", "testschema.profile");
        mAssert.assertManyToManyAssociation("testschema.profile", "friendRequestees", "testschema.profile");
        mAssert.assertOneToOneAssociation("testschema.profile", "spouseTo", "testschema.profile");
        mAssert.assertOneToOneAssociation("testschema.profile", "spouseFrom", "testschema.profile");
        
        // 17. Validate timesheet
        mAssert.assertEntityExists("testschema.timesheet");
        mAssert.assertComponentIdentity("testschema.timesheet", "employeeId", "date");
        mAssert.assertOneToMany("testschema.timesheet", "activities", "testschema.activity");
        mAssert.assertSimpleProperty("testschema.timesheet", "expectedHours", PropertyType.BIG_DECIMAL);
     
        // 18. Validate types
        mAssert.assertEntityExists("testschema.type");
        mAssert.assertSimpleIdentity("testschema.type", "type6", PropertyType.LONG);
        mAssert.assertSimpleProperty("testschema.type", "type1", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("testschema.type", "type2", PropertyType.BOOLEAN);
        mAssert.assertSimpleProperty("testschema.type", "type3", PropertyType.BYTE);
        mAssert.assertSimpleProperty("testschema.type", "type4", PropertyType.SHORT);
        mAssert.assertSimpleProperty("testschema.type", "type5", PropertyType.LONG);
        mAssert.assertSimpleProperty("testschema.type", "type7", PropertyType.BIG_DECIMAL);
        mAssert.assertSimpleProperty("testschema.type", "type8", PropertyType.DOUBLE);
        mAssert.assertSimpleProperty("testschema.type", "type9", PropertyType.FLOAT);
        mAssert.assertSimpleProperty("testschema.type", "type10", PropertyType.TIME);
        //mAssert.assertSimpleProperty("testschema.type", "type11", PropertyType.LONG);
        mAssert.assertSimpleProperty("testschema.type", "type12", PropertyType.DATE);
        mAssert.assertSimpleProperty("testschema.type", "type13", PropertyType.TIMESTAMP);
        mAssert.assertSimpleProperty("testschema.type", "type14", PropertyType.BINARY);
        //mAssert.assertSimpleProperty("testschema.type", "type15", PropertyType.LONG);
        mAssert.assertSimpleProperty("testschema.type", "type16", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.type", "type17", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.type", "type18", PropertyType.BLOB);
        mAssert.assertSimpleProperty("testschema.type", "type19", PropertyType.TEXT);
        mAssert.assertSimpleProperty("testschema.type", "type20", PropertyType.STRING);

        // 19. Validate user
        mAssert.assertEntityExists("testschema.user");
        mAssert.assertSimpleIdentity("testschema.user", "username", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.user", "password", PropertyType.STRING);
        mAssert.assertOneToOneParent("testschema.user", "profile", "testschema.profile");
        mAssert.assertOneToMany("testschema.user", "ownerBugs", "testschema.bug"); 
        mAssert.assertOneToMany("testschema.user", "reporterBugs", "testschema.bug");
    
        // 20. Validate vehicle
        mAssert.assertEntityExists("testschema.vehicle");
        mAssert.assertSimpleIdentity("testschema.vehicle", "regNumber", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.vehicle", "model", PropertyType.STRING);
        mAssert.assertOneToMany("testschema.vehicle", "parkingPermits", "testschema.parkingPermit");
    
        // 21. Validate bugs
        mAssert.assertEntityExists("testschema.bug");
        mAssert.assertSimpleIdentity("testschema.bug", "bugId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("testschema.bug", "title", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.bug", "description", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.bug", "open", PropertyType.BOOLEAN);
        mAssert.assertManyToOne("testschema.bug", "owner", "testschema.user"); 
        mAssert.assertManyToOne("testschema.bug", "reporter", "testschema.user");
        
        // 23. Validate noSchema
        mAssert.assertEntityExists("noSchema");
        mAssert.assertSimpleIdentity("noSchema", "noSchemaId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("noSchema", "label", PropertyType.STRING);
        mAssert.assertSimpleProperty("noSchema", "flag", PropertyType.BOOLEAN);
        mAssert.assertManyToOne("noSchema", "user", "testschema.user");
        
    }
    
    @Test
    public void testReverseEngineerH2WithoutNaturalIdentities() throws ModelException {
    	
        // Set up RevengConfig
        RevengConfig revengConfig = new RevengConfig();
        revengConfig.setModelName("model");
        revengConfig.setCreateNaturalIdentities(false);
        
        // Create ReverseEngineer
        ReverseEngineerImpl reveng = new ReverseEngineerImpl(revengConfig);
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(h2);
        
        // Validate model
        Assert.assertNotNull(model);
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        
        // Validate catalog
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertEquals("greenvine", catalog.getName().toString());
        Assert.assertEquals(23, catalog.getEntityCount());
        
        // Create ModelAssert object to test entities
        ModelAssert mAssert = new ModelAssert(catalog);
        
        // 1. Validate contract
        mAssert.assertEntityExists("dbo.contract");
        mAssert.assertOneToOneIdentity("dbo.contract", "employee", "dbo.employee");
        mAssert.assertSimpleProperty("dbo.contract", "terms", PropertyType.STRING);
        
        // 2. Validate desk
        mAssert.assertEntityExists("dbo.desk");
        mAssert.assertSimpleIdentity("dbo.desk", "deskId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("dbo.desk", "code", PropertyType.STRING);
        mAssert.assertOneToOneAssociation("dbo.desk", "employee", "dbo.employee");
        
        // 3. Validate employee
        mAssert.assertEntityExists("dbo.employee");
        mAssert.assertSimpleIdentity("dbo.employee", "employeeId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("dbo.employee", "email", PropertyType.STRING);
        mAssert.assertSimpleProperty("dbo.employee", "firstName", PropertyType.STRING);
        mAssert.assertSimpleProperty("dbo.employee", "lastName", PropertyType.STRING);
        mAssert.assertSimpleProperty("dbo.employee", "hourlyCost", PropertyType.BIG_DECIMAL);
        mAssert.assertSimpleProperty("dbo.employee", "dailyWorkingHours", PropertyType.BIG_DECIMAL);
        mAssert.assertOneToOneAssociation("dbo.employee", "desk", "dbo.desk");
        mAssert.assertOneToOneParent("dbo.employee", "contract", "dbo.contract");
        mAssert.assertOneToOneChild("dbo.employee", "user", "dbo.user");
        mAssert.assertManyToOne("dbo.employee", "manager", "dbo.employee");
        mAssert.assertOneToMany("dbo.employee", "employees", "dbo.employee");
        mAssert.assertOneToMany("dbo.employee", "umbrellas", "dbo.umbrella");
        mAssert.assertManyToOneAssociation("dbo.employee", "employeeMentor", "dbo.employee"); 
        mAssert.assertOneToManyAssociation("dbo.employee", "employeeMentorees", "dbo.employee"); 
        
        // 4. Validate group
        mAssert.assertEntityExists("dbo.group");
        mAssert.assertSimpleIdentity("dbo.group", "groupId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("dbo.group", "groupname", PropertyType.STRING);
        mAssert.assertManyToManyAssociation("dbo.group", "users", "dbo.user");
        
        // 5. Validate stand
        mAssert.assertEntityExists("dbo.stand");
        mAssert.assertSimpleIdentity("dbo.stand", "standId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("dbo.stand", "description", PropertyType.STRING);
        mAssert.assertOneToManyAssociation("dbo.stand", "umbrellas", "dbo.umbrella");
        
        // 6. Validate umbrella
        mAssert.assertEntityExists("dbo.umbrella");
        mAssert.assertSimpleIdentity("dbo.umbrella", "umbrellaId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("dbo.umbrella", "colour", PropertyType.STRING);
        mAssert.assertManyToOneAssociation("dbo.umbrella", "stand", "dbo.stand");
        
        // 7. Validate user
        mAssert.assertEntityExists("dbo.user");
        mAssert.assertSimpleIdentity("dbo.user", "userId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("dbo.user", "username", PropertyType.STRING);
        mAssert.assertSimpleProperty("dbo.user", "password", PropertyType.STRING);
        mAssert.assertManyToManyAssociation("dbo.user", "groups", "dbo.group");
        
        // 8. Validate activity
        mAssert.assertEntityExists("testschema.activity");
        mAssert.assertSimpleIdentity("testschema.activity", "activityId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("testschema.activity", "description", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.activity", "hours", PropertyType.BIG_DECIMAL);
        mAssert.assertManyToOne("testschema.activity", "timesheet", "testschema.timesheet");
        
        // 9. Validate address
        mAssert.assertEntityExists("testschema.address");
        mAssert.assertSimpleIdentity("testschema.address", "addressId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("testschema.address", "houseNumber", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.address", "streetName", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.address", "postCode", PropertyType.STRING);
        mAssert.assertOneToMany("testschema.address", "consignments", "testschema.consignment");
        
        // 10. Validate consignment
        mAssert.assertEntityExists("testschema.consignment");
        mAssert.assertSimpleIdentity("testschema.consignment", "consignmentId", PropertyType.INTEGER);
        mAssert.assertManyToOne("testschema.consignment", "customer", "testschema.customer");
        mAssert.assertSimpleProperty("testschema.consignment", "consignmentDate", PropertyType.DATE);
        mAssert.assertManyToOne("testschema.consignment", "address", "testschema.address");
        
        // 11. Validate customer
        mAssert.assertEntityExists("testschema.customer");
        mAssert.assertOneToOneIdentity("testschema.customer", "person", "testschema.person");
        mAssert.assertSimpleProperty("testschema.customer", "loyaltyPoints", PropertyType.INTEGER);
        mAssert.assertOneToMany("testschema.customer", "consignments", "testschema.consignment");
        
        // 12. Validate parkingPermit
        mAssert.assertEntityExists("testschema.parkingPermit");
        mAssert.assertComponentIdentity("testschema.parkingPermit", "regNumber", "date"); 
        mAssert.assertSimpleProperty("testschema.parkingPermit", "value", PropertyType.BIG_DECIMAL);
        mAssert.assertManyToOne("testschema.parkingPermit", "vehicle", "testschema.vehicle");
        mAssert.assertOneToOneParent("testschema.parkingPermit", "parkingPermitPayment", "testschema.parkingPermitPayment");

        // 13. Validate parkingPermitPayment
        mAssert.assertEntityExists("testschema.parkingPermitPayment");
        mAssert.assertOneToOneIdentity("testschema.parkingPermitPayment", "parkingPermit", "testschema.parkingPermit");
        mAssert.assertSimpleProperty("testschema.parkingPermitPayment", "paymentDate", PropertyType.TIMESTAMP);

        // 14. Validate passport
        mAssert.assertEntityExists("testschema.passport");
        mAssert.assertSimpleIdentity("testschema.passport", "passportNr", PropertyType.STRING);
        mAssert.assertOneToOneChild("testschema.passport", "person", "testschema.person");
        mAssert.assertSimpleProperty("testschema.passport", "expiryDate", PropertyType.DATE);
        
        // 15. Validate person
        mAssert.assertEntityExists("testschema.person");
        mAssert.assertComponentIdentity("testschema.person", "firstName", "lastName");
        mAssert.assertOneToOneParent("testschema.person", "customer", "testschema.customer");
        mAssert.assertOneToOneParent("testschema.person", "passport", "testschema.passport");
        mAssert.assertSimpleProperty("testschema.person", "birthday", PropertyType.DATE);

        // 16. Validate profile
        mAssert.assertEntityExists("testschema.profile");
        mAssert.assertSimpleIdentity("testschema.profile", "profileId", PropertyType.INTEGER);
        mAssert.assertOneToOneChild("testschema.profile", "user", "testschema.user");
        mAssert.assertSimpleProperty("testschema.profile", "screenName", PropertyType.STRING);
        mAssert.assertManyToManyAssociation("testschema.profile", "friendRequesters", "testschema.profile");
        mAssert.assertManyToManyAssociation("testschema.profile", "friendRequestees", "testschema.profile");
        mAssert.assertOneToOneAssociation("testschema.profile", "spouseTo", "testschema.profile");
        mAssert.assertOneToOneAssociation("testschema.profile", "spouseFrom", "testschema.profile");
        
        // 17. Validate timesheet
        mAssert.assertEntityExists("testschema.timesheet");
        mAssert.assertComponentIdentity("testschema.timesheet", "employeeId", "date");
        mAssert.assertOneToMany("testschema.timesheet", "activities", "testschema.activity");
        mAssert.assertSimpleProperty("testschema.timesheet", "expectedHours", PropertyType.BIG_DECIMAL);
     
        // 18. Validate types
        mAssert.assertEntityExists("testschema.type");
        mAssert.assertSimpleIdentity("testschema.type", "type6", PropertyType.LONG);
        mAssert.assertSimpleProperty("testschema.type", "type1", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("testschema.type", "type2", PropertyType.BOOLEAN);
        mAssert.assertSimpleProperty("testschema.type", "type3", PropertyType.BYTE);
        mAssert.assertSimpleProperty("testschema.type", "type4", PropertyType.SHORT);
        mAssert.assertSimpleProperty("testschema.type", "type5", PropertyType.LONG);
        mAssert.assertSimpleProperty("testschema.type", "type7", PropertyType.BIG_DECIMAL);
        mAssert.assertSimpleProperty("testschema.type", "type8", PropertyType.DOUBLE);
        mAssert.assertSimpleProperty("testschema.type", "type9", PropertyType.FLOAT);
        mAssert.assertSimpleProperty("testschema.type", "type10", PropertyType.TIME);
        //mAssert.assertSimpleProperty("testschema.type", "type11", PropertyType.LONG);
        mAssert.assertSimpleProperty("testschema.type", "type12", PropertyType.DATE);
        mAssert.assertSimpleProperty("testschema.type", "type13", PropertyType.TIMESTAMP);
        mAssert.assertSimpleProperty("testschema.type", "type14", PropertyType.BINARY);
        //mAssert.assertSimpleProperty("testschema.type", "type15", PropertyType.LONG);
        mAssert.assertSimpleProperty("testschema.type", "type16", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.type", "type17", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.type", "type18", PropertyType.BLOB);
        mAssert.assertSimpleProperty("testschema.type", "type19", PropertyType.TEXT);
        mAssert.assertSimpleProperty("testschema.type", "type20", PropertyType.STRING);

        // 19. Validate user
        mAssert.assertEntityExists("testschema.user");
        mAssert.assertSimpleIdentity("testschema.user", "username", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.user", "password", PropertyType.STRING);
        mAssert.assertOneToOneParent("testschema.user", "profile", "testschema.profile");
        mAssert.assertOneToMany("testschema.user", "ownerBugs", "testschema.bug"); 
        mAssert.assertOneToMany("testschema.user", "reporterBugs", "testschema.bug");
    
        // 20. Validate vehicle
        mAssert.assertEntityExists("testschema.vehicle");
        mAssert.assertSimpleIdentity("testschema.vehicle", "regNumber", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.vehicle", "model", PropertyType.STRING);
        mAssert.assertOneToMany("testschema.vehicle", "parkingPermits", "testschema.parkingPermit");
    
        // 21. Validate bugs
        mAssert.assertEntityExists("testschema.bug");
        mAssert.assertSimpleIdentity("testschema.bug", "bugId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("testschema.bug", "title", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.bug", "description", PropertyType.STRING);
        mAssert.assertSimpleProperty("testschema.bug", "open", PropertyType.BOOLEAN);
        mAssert.assertManyToOne("testschema.bug", "owner", "testschema.user"); 
        mAssert.assertManyToOne("testschema.bug", "reporter", "testschema.user");
        mAssert.assertOneToMany("testschema.bug", "comments", "testschema.comment");
        
        // 22. Validate comments
        mAssert.assertEntityExists("testschema.comment");
        mAssert.assertSimpleIdentity("testschema.comment", "commentId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("testschema.comment", "comment", PropertyType.STRING);
        mAssert.assertManyToOne("testschema.comment", "bug", "testschema.bug"); 
        mAssert.assertManyToOne("testschema.comment", "user", "testschema.user");
        
        // 23. Validate noSchema
        mAssert.assertEntityExists("noSchema");
        mAssert.assertSimpleIdentity("noSchema", "noSchemaId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("noSchema", "label", PropertyType.STRING);
        mAssert.assertSimpleProperty("noSchema", "flag", PropertyType.BOOLEAN);
        mAssert.assertManyToOne("noSchema", "user", "testschema.user");
    
    }
    
    @Test
    public void testReverseEngineerMySql() throws ModelException {
    	
        // Set up RevengConfig
        RevengConfig revengConfig = new RevengConfig();
        revengConfig.setModelName("model");
        revengConfig.setCreateNaturalIdentities(false);
        
        // Create ReverseEngineer
        ReverseEngineerImpl reveng = new ReverseEngineerImpl(revengConfig);
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(mysql);
        
     // Validate model
        Assert.assertNotNull(model);
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        
        // Validate catalog
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertEquals("greenvine", catalog.getName().toString());
        Assert.assertEquals(6, catalog.getEntityCount());
        
        // Create ModelAssert object to test entities
        ModelAssert mAssert = new ModelAssert(catalog);
        
        // 1. Validate user
        mAssert.assertEntityExists("user");
        mAssert.assertSimpleIdentity("user", "userId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("user", "username", PropertyType.STRING);
        mAssert.assertSimpleProperty("user", "password", PropertyType.STRING);
        
        // TODO more assertions
        
    }
    
}
