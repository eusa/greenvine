package net.sourceforge.greenvine.dbextractor.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import junit.framework.Assert;
import net.sourceforge.greenvine.dbextractor.DatabaseExtractor;
import net.sourceforge.greenvine.dbextractor.DatabaseExtractorException;
import net.sourceforge.greenvine.dbextractor.testutils.DatabaseAssert;
import net.sourceforge.greenvine.dbextractor.testutils.DatabaseExtractorConfigFactory;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.CaseStrategy;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConvention;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

import org.h2.util.ScriptReader;
import org.junit.Before;
import org.junit.Test;

public class JdbcDatabaseExtractorImplTest {

    private static final String VALIDATION_REGEX = "^([A-Za-z_0-9]+)*$";
    private static final String CATALOG = "GREENVINE_DB";
	private static final String JDBC_URL = "jdbc:h2:mem:~/" + CATALOG + ";DB_CLOSE_DELAY=-1";
    private static final String JDBC_DRIVER = "org.h2.Driver";

    @Before
	public void setUp() throws Exception {
		
		// Get database connection
		Class.forName(JDBC_DRIVER);
		final Connection connection = DriverManager.getConnection(JDBC_URL);

		// Create schema
		try {
		    createDatabase(connection, "src/test/resources/test-schema-h2.sql");
		    connection.commit();
		} finally {
		    // Note: need to delay database close
		    // See JDBC_URL parameter
		    connection.close();
		}
        		
	}

    private void createDatabase(final Connection connection, final String schemaFile)
            throws FileNotFoundException, SQLException {
        final File ddlFile = new File(schemaFile);
		final Reader reader = new FileReader(ddlFile);
		final ScriptReader scriptReader = new ScriptReader(reader);
		while (true) {
			final String statement = scriptReader.readStatement();
		    if (statement == null) {
		        break;
		    } 
		    final PreparedStatement sqlStatement = connection.prepareStatement(statement);
		    try {
		        sqlStatement.execute();
		    } finally {
		        sqlStatement.close();
		    }
		}
    }
	
	@Test
	public void testExtractDatabaseH2() throws DatabaseExtractorException, ModelException {
		
		// Get DatabaseExtractorConfig
		final JdbcDatabaseExtractorConfig config = DatabaseExtractorConfigFactory.getTestDatabaseExtractorConfig();
		
		// Get DatabaseNamingConvention
		final RdbmsNamingConventions conventions = getTestNamingConvention();
		
		// Extract database
		final DatabaseExtractor dbExtractor = new JdbcDatabaseExtractorImpl(config, conventions);
		final Database database = dbExtractor.extractDatabase();
		
		// Validate
		validateDatabase(database); 
		
	}
	
	
    public void validateDatabase(final Database database) throws ModelException {

		// Test database
		Assert.assertNotNull(database);
		Assert.assertEquals(Integer.valueOf(28), Integer.valueOf(database.getTableCount()));
		
		// Create DatabaseAssert instance
		final DatabaseAssert dbAssert = new DatabaseAssert(database);
		
		// Test DBO.TBL_USER
		dbAssert.assertTableExists("DBO.TBL_USER", createNameArray("USER_ID", "USERNAME", "PASSWORD"));
		dbAssert.assertPrimaryKeyExists("DBO.PK_USER", createNameArray("USER_ID"));
		dbAssert.assertUniqueKeyExists("DBO.UK_USER1_UNIQUE", createNameArray("USERNAME"));
		
		// Test TBL_GROUP
        dbAssert.assertTableExists("DBO.TBL_GROUP", createNameArray("GROUP_ID", "GROUPNAME"));
        dbAssert.assertPrimaryKeyExists("DBO.PK_GROUP", createNameArray("GROUP_ID"));
        dbAssert.assertUniqueKeyExists("DBO.UK_GROUP1_UNIQUE", createNameArray("GROUPNAME"));
        
		// Test TBL_USER_GROUP
        dbAssert.assertTableExists("DBO.TBL_GROUP_USER", createNameArray("FK_USER_ID", "FK_GROUP_ID"));
        dbAssert.assertPrimaryKeyExists("DBO.PK_GROUP_USER", createNameArray("FK_USER_ID", "FK_GROUP_ID"));
        dbAssert.assertForeignKeyExists("DBO.FK_GROUP_USER_USER", "DBO.TBL_GROUP_USER", "DBO.TBL_USER");
        dbAssert.assertForeignKeyExists("DBO.FK_GROUP_USER_GROUP", "DBO.TBL_GROUP_USER", "DBO.TBL_GROUP");
        
		// Test TBL_EMPLOYEE
        dbAssert.assertTableExists("DBO.TBL_EMPLOYEE", createNameArray("EMPLOYEE_ID", "FK_USER_ID", "FK_MANAGER_ID", "FIRST_NAME", "LAST_NAME", "EMAIL", "DAILY_WORKING_HOURS", "HOURLY_COST"));
        dbAssert.assertPrimaryKeyExists("DBO.PK_EMPLOYEE", createNameArray("EMPLOYEE_ID"));
        dbAssert.assertUniqueKeyExists("DBO.UK_EMPLOYEE1_UNIQUE", createNameArray("EMAIL"));
        dbAssert.assertUniqueKeyExists("DBO.UK_EMPLOYEE2_UNIQUE", createNameArray("FIRST_NAME", "LAST_NAME"));
        dbAssert.assertUniqueKeyExists("DBO.UK_EMPLOYEE3_UNIQUE", createNameArray("FK_USER_ID"));
        dbAssert.assertForeignKeyExists("DBO.FK_EMPLOYEE_USER", "DBO.TBL_EMPLOYEE", "DBO.TBL_USER");
        dbAssert.assertForeignKeyExists("DBO.FK_EMPLOYEE_MANAGER", "DBO.TBL_EMPLOYEE", "DBO.TBL_EMPLOYEE");
        
        // Test TBL_EMPLOYEE_MENTOR
        dbAssert.assertTableExists("DBO.TBL_EMPLOYEE_MENTOR", createNameArray("EMPLOYEE_MENTOR_ID", "MENTOR_ID", "MENTOREE_ID"));
        dbAssert.assertPrimaryKeyExists("DBO.PK_EMPLOYEE_MENTOR", createNameArray("EMPLOYEE_MENTOR_ID"));
        dbAssert.assertUniqueKeyExists("DBO.UK_EMPLOYEE_MENTOR1_UNIQUE", createNameArray("MENTOREE_ID"));
        dbAssert.assertForeignKeyExists("DBO.FK_EMPLOYEE_MENTOREE", "DBO.TBL_EMPLOYEE_MENTOR", "DBO.TBL_EMPLOYEE");
        dbAssert.assertForeignKeyExists("DBO.FK_EMPLOYEE_MENTOR", "DBO.TBL_EMPLOYEE_MENTOR", "DBO.TBL_EMPLOYEE");
        
		// Test TBL_CONTRACT
        dbAssert.assertTableExists("DBO.TBL_CONTRACT", createNameArray("FK_EMPLOYEE_ID", "TERMS"));
        dbAssert.assertPrimaryKeyExists("DBO.PK_CONTRACT", createNameArray("FK_EMPLOYEE_ID"));
        dbAssert.assertForeignKeyExists("DBO.FK_CONTRACT_EMPLOYEE", "DBO.TBL_CONTRACT", "DBO.TBL_EMPLOYEE");
        
		// Test TBL_UMBRELLA
        dbAssert.assertTableExists("DBO.TBL_UMBRELLA", createNameArray("UMBRELLA_ID", "FK_EMPLOYEE_ID", "COLOUR"));
        dbAssert.assertPrimaryKeyExists("DBO.PK_UMBRELLA", createNameArray("UMBRELLA_ID"));
        dbAssert.assertForeignKeyExists("DBO.FK_UMBRELLA_EMPLOYEE", "DBO.TBL_UMBRELLA", "DBO.TBL_EMPLOYEE");
        
		// Test TBL_STAND
        dbAssert.assertTableExists("DBO.TBL_STAND", createNameArray("STAND_ID", "DESCRIPTION"));
        dbAssert.assertPrimaryKeyExists("DBO.PK_STAND", createNameArray("STAND_ID"));
        
		// Test TBL_UMBRELLA_STAND
        dbAssert.assertTableExists("DBO.TBL_STAND_UMBRELLA", createNameArray("FK_STAND_ID", "FK_UMBRELLA_ID"));
        dbAssert.assertPrimaryKeyExists("DBO.PK_STAND_UMBRELLA", createNameArray("FK_STAND_ID", "FK_UMBRELLA_ID"));
        dbAssert.assertUniqueKeyExists("DBO.UK_STAND_UMBRELLA1_UNIQUE", createNameArray("FK_UMBRELLA_ID"));
        dbAssert.assertForeignKeyExists("DBO.FK_STAND_UMBRELLA_UMBRELLA", "DBO.TBL_STAND_UMBRELLA", "DBO.TBL_UMBRELLA");
        dbAssert.assertForeignKeyExists("DBO.FK_STAND_UMBRELLA_STAND", "DBO.TBL_STAND_UMBRELLA", "DBO.TBL_STAND");
        
		// Test TBL_DESK
        dbAssert.assertTableExists("DBO.TBL_DESK", createNameArray("DESK_ID", "CODE"));
        dbAssert.assertPrimaryKeyExists("DBO.PK_DESK", createNameArray("DESK_ID"));
        
		
		// Test DBO.TBL_DESK_EMPLOYEE
        dbAssert.assertTableExists("DBO.TBL_DESK_EMPLOYEE", createNameArray("FK_DESK_ID", "FK_EMPLOYEE_ID"));
        dbAssert.assertPrimaryKeyExists("DBO.PK_DESK_EMPLOYEE", createNameArray("FK_DESK_ID", "FK_EMPLOYEE_ID"));
        dbAssert.assertUniqueKeyExists("DBO.UK_DESK_EMPLOYEE1_UNIQUE", createNameArray("FK_DESK_ID"));
        dbAssert.assertUniqueKeyExists("DBO.UK_DESK_EMPLOYEE2_UNIQUE", createNameArray("FK_EMPLOYEE_ID"));
        dbAssert.assertForeignKeyExists("DBO.FK_DESK_EMPLOYEE_DESK", "DBO.TBL_DESK_EMPLOYEE", "DBO.TBL_DESK");
        dbAssert.assertForeignKeyExists("DBO.FK_DESK_EMPLOYEE_EMPLOYEE", "DBO.TBL_DESK_EMPLOYEE", "DBO.TBL_EMPLOYEE");
        
		// Test TBL_MAILBOX
        // TODO [minor] reinstate when Hibernate JPA supports non-primary key based relations
        
		// Test TEST.TBL_TIMESHEET
        dbAssert.assertTableExists("TEST.TBL_TIMESHEET", createNameArray("FK_EMPLOYEE_ID", "DATE", "EXPECTED_HOURS"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_TIMESHEET", createNameArray("FK_EMPLOYEE_ID", "DATE"));
        dbAssert.assertForeignKeyExists("TEST.FK_TIMESHEET_EMPLOYEE", "TEST.TBL_TIMESHEET", "DBO.TBL_EMPLOYEE");
        
        // Test TEST.TBL_ACTIVITY
        dbAssert.assertTableExists("TEST.TBL_ACTIVITY", createNameArray("ACTIVITY_ID", "FK_EMPLOYEE_ID", "FK_DATE", "HOURS", "DESCRIPTION"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_ACTIVITY", createNameArray("ACTIVITY_ID"));
        dbAssert.assertForeignKeyExists("TEST.FK_ACTIVITY_TIMESHEET", "TEST.TBL_ACTIVITY", "TEST.TBL_TIMESHEET");
        
        // Test TEST.TBL_VEHICLE
        dbAssert.assertTableExists("TEST.TBL_VEHICLE", createNameArray("REG_NUMBER", "MODEL"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_VEHICLE", createNameArray("REG_NUMBER"));
        
        // Test TEST.TBL_PARKING_PERMIT
        dbAssert.assertTableExists("TEST.TBL_PARKING_PERMIT", createNameArray("FK_REG_NUMBER", "DATE", "VALUE"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_PARKING_PERMIT", createNameArray("FK_REG_NUMBER", "DATE"));
        dbAssert.assertForeignKeyExists("TEST.FK_PARKING_PERMIT_VEHICLE", "TEST.TBL_PARKING_PERMIT", "TEST.TBL_VEHICLE");
        
        // Test TEST.TBL_PARKING_PERMIT_PAYMENT
        dbAssert.assertTableExists("TEST.TBL_PARKING_PERMIT_PAYMENT", createNameArray("FK_REG_NUMBER", "FK_PARKING_PERMIT_DATE", "PAYMENT_DATE"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_PARKING_PERMIT_PAYMENT", createNameArray("FK_REG_NUMBER", "FK_PARKING_PERMIT_DATE"));
        dbAssert.assertForeignKeyExists("TEST.FK_PARKING_PERMIT_PAYMENT_PARKING_PERMIT", "TEST.TBL_PARKING_PERMIT_PAYMENT", "TEST.TBL_PARKING_PERMIT");
        
        // Test TEST.TBL_USER
        dbAssert.assertTableExists("TEST.TBL_USER", createNameArray("USERNAME", "PASSWORD"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_USER", createNameArray("USERNAME"));
        
        // Test TEST.TBL_PROFILE
        dbAssert.assertTableExists("TEST.TBL_PROFILE", createNameArray("PROFILE_ID", "FK_USERNAME", "SCREEN_NAME"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_PROFILE", createNameArray("PROFILE_ID"));
        dbAssert.assertUniqueKeyExists("TEST.UK_PROFILE1_UNIQUE", createNameArray("FK_USERNAME"));
        dbAssert.assertForeignKeyExists("TEST.FK_PROFILE_USER", "TEST.TBL_PROFILE", "TEST.TBL_USER");
        
        // Test TEST.TBL_FRIEND
        dbAssert.assertTableExists("TEST.TBL_FRIEND", createNameArray("REQUESTER_ID", "REQUESTEE_ID"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_FRIEND", createNameArray("REQUESTER_ID", "REQUESTEE_ID"));
        dbAssert.assertForeignKeyExists("TEST.FK_FRIEND_REQUESTER", "TEST.TBL_FRIEND", "TEST.TBL_PROFILE");
        dbAssert.assertForeignKeyExists("TEST.FK_FRIEND_REQUESTEE", "TEST.TBL_FRIEND", "TEST.TBL_PROFILE");
        
        // Test TEST.TBL_SPOUSE
        dbAssert.assertTableExists("TEST.TBL_SPOUSE", createNameArray("SPOUSE_TO_ID", "SPOUSE_FROM_ID"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_SPOUSE", createNameArray("SPOUSE_TO_ID", "SPOUSE_FROM_ID"));
        dbAssert.assertForeignKeyExists("TEST.FK_SPOUSE_TO", "TEST.TBL_SPOUSE", "TEST.TBL_PROFILE");
        dbAssert.assertForeignKeyExists("TEST.FK_SPOUSE_FROM", "TEST.TBL_SPOUSE", "TEST.TBL_PROFILE");
        dbAssert.assertUniqueKeyExists("TEST.UK_SPOUSE1_UNIQUE", createNameArray("SPOUSE_FROM_ID"));
        dbAssert.assertUniqueKeyExists("TEST.UK_SPOUSE2_UNIQUE", createNameArray("SPOUSE_TO_ID"));
        
        // Test TEST.TBL_PERSON
        dbAssert.assertTableExists("TEST.TBL_PERSON", createNameArray("FIRST_NAME", "LAST_NAME", "BIRTHDAY"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_PERSON", createNameArray("FIRST_NAME", "LAST_NAME"));
        
        // Test TEST.TBL_CUSTOMER
        dbAssert.assertTableExists("TEST.TBL_CUSTOMER", createNameArray("FK_FIRST_NAME", "FK_LAST_NAME", "LOYALTY_POINTS"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_CUSTOMER", createNameArray("FK_FIRST_NAME", "FK_LAST_NAME"));
        dbAssert.assertForeignKeyExists("TEST.FK_CUSTOMER_PERSON", "TEST.TBL_CUSTOMER", "TEST.TBL_PERSON");
        
        // Test TEST.TBL_PASSPORT
        dbAssert.assertTableExists("TEST.TBL_PASSPORT", createNameArray("PASSPORT_NR", "FK_FIRST_NAME", "FK_LAST_NAME", "EXPIRY_DATE"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_PASSPORT", createNameArray("PASSPORT_NR"));
        dbAssert.assertUniqueKeyExists("TEST.UK_PASSPORT1_UNIQUE", createNameArray("FK_FIRST_NAME", "FK_LAST_NAME"));
        dbAssert.assertForeignKeyExists("TEST.FK_PASSPORT_PERSON", "TEST.TBL_PASSPORT", "TEST.TBL_PERSON");
        
        // Test TEST.TBL_ADDRESS
        dbAssert.assertTableExists("TEST.TBL_ADDRESS", createNameArray("ADDRESS_ID", "HOUSE_NUMBER", "STREET_NAME", "POST_CODE"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_ADDRESS", createNameArray("ADDRESS_ID"));
        dbAssert.assertUniqueKeyExists("TEST.UK_ADDRESS1_UNIQUE", createNameArray("HOUSE_NUMBER", "STREET_NAME"));
        
        // Test TEST.TBL_CONSIGNMENT
        dbAssert.assertTableExists("TEST.TBL_CONSIGNMENT", createNameArray("CONSIGNMENT_ID", "FK_FIRST_NAME", "FK_LAST_NAME", "CONSIGNMENT_DATE", "FK_ADDRESS_ID"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_CONSIGNMENT", createNameArray("CONSIGNMENT_ID"));
        dbAssert.assertUniqueKeyExists("TEST.UK_CONSIGNMENT1_UNIQUE", createNameArray("FK_FIRST_NAME","FK_LAST_NAME", "CONSIGNMENT_DATE"));
        dbAssert.assertForeignKeyExists("TEST.FK_CONSIGNMENT_CUSTOMER", "TEST.TBL_CONSIGNMENT", "TEST.TBL_CUSTOMER");
        dbAssert.assertForeignKeyExists("TEST.FK_CONSIGNMENT_ADDRESS", "TEST.TBL_CONSIGNMENT", "TEST.TBL_ADDRESS");
     
        // Test TEST.TBL_ADDRESS
        dbAssert.assertTableExists("TEST.TBL_TYPES");
        //dbAssert.assertPrimaryKeyExists("TEST.PK_TYPES", createNameArray("TYPE_6"));
        
        // Test TEST.TBL_BUGS
        dbAssert.assertTableExists("TEST.TBL_BUGS", createNameArray("BUG_ID", "OWNER", "REPORTER", "TITLE", "DESCRIPTION", "OPEN"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_BUGS", createNameArray("BUG_ID"));
        dbAssert.assertForeignKeyExists("TEST.FK_BUGS_USER_OWNER", "TEST.TBL_BUGS", "TEST.TBL_USER");
        dbAssert.assertForeignKeyExists("TEST.FK_BUGS_USER_REPORTER", "TEST.TBL_BUGS", "TEST.TBL_USER");
     
        // Test TEST.TBL_COMMENTS
        dbAssert.assertTableExists("TEST.TBL_COMMENTS", createNameArray("COMMENT_ID", "BUG_ID", "USERNAME", "COMMENT"));
        dbAssert.assertPrimaryKeyExists("TEST.PK_COMMENTS", createNameArray("COMMENT_ID"));
        dbAssert.assertForeignKeyExists("TEST.FK_COMMENTS_BUGS", "TEST.TBL_COMMENTS", "TEST.TBL_BUGS");
        dbAssert.assertForeignKeyExists("TEST.FK_COMMENTS_USER", "TEST.TBL_COMMENTS", "TEST.TBL_USER");
     
    }	
	
    private CharSequence[] createNameArray(final String ... string) {
        final CharSequence[] names = new CharSequence[string.length];
        System.arraycopy(string, 0, names, 0, string.length);
        return names;
    }

    public  RdbmsNamingConventions getTestNamingConvention() {
        
        final RdbmsNamingConventions namingConvention = new RdbmsNamingConventions();
        namingConvention.setDatabase(new RdbmsNamingConvention("_", "DB", null, VALIDATION_REGEX, CaseStrategy.UPPERCASE));
        namingConvention.setTable(new RdbmsNamingConvention("_", "TBL", null, VALIDATION_REGEX, CaseStrategy.UPPERCASE));
        namingConvention.setView(new RdbmsNamingConvention("_", "VIEW", null, VALIDATION_REGEX, CaseStrategy.UPPERCASE));
        namingConvention.setPrimaryColumn(new RdbmsNamingConvention("_", "PK", null, VALIDATION_REGEX, CaseStrategy.UPPERCASE));
        namingConvention.setForeignColumn(new RdbmsNamingConvention("_", "FK", "ID", VALIDATION_REGEX, CaseStrategy.UPPERCASE));
        namingConvention.setDataColumn(new RdbmsNamingConvention("_", null, null, VALIDATION_REGEX, CaseStrategy.UPPERCASE));
        namingConvention.setForeignKey(new RdbmsNamingConvention("_", "FK", null, VALIDATION_REGEX, CaseStrategy.UPPERCASE));
        namingConvention.setPrimaryKey(new RdbmsNamingConvention("_", "PK", null, VALIDATION_REGEX, CaseStrategy.UPPERCASE));
        namingConvention.setUniqueKey(new RdbmsNamingConvention("_", "UK", "UNIQUE", VALIDATION_REGEX, CaseStrategy.UPPERCASE));
        return namingConvention;
        
    }
    
}
