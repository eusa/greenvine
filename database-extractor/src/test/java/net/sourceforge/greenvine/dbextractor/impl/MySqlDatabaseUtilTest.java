package net.sourceforge.greenvine.dbextractor.impl;

import net.sourceforge.greenvine.dbextractor.testutils.MySqlDatabaseUtil;

import org.junit.Test;

public class MySqlDatabaseUtilTest {
	
	@Test
	public void testCreateDatabase() throws Exception {
		MySqlDatabaseUtil.createDatabase("src/test/resources/test-schema-mysql.sql", "TEST_DB");
	}

}
