package net.sourceforge.greenvine.dbextractor.impl;

import net.sourceforge.greenvine.dbextractor.testutils.H2DatabaseUtil;

import org.junit.Test;

public class H2DatabaseUtilTest {
	
	@Test
	public void testCreateDatabase() throws Exception {
		H2DatabaseUtil.createDatabase("src/test/resources/test-schema-h2.sql", "TEST_DB");
	}

}
