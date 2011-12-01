package net.sourceforge.greenvine.dbextractor.impl;

import java.io.File;

import junit.framework.Assert;
import net.sourceforge.greenvine.dbextractor.DatabaseExtractor;
import net.sourceforge.greenvine.dbextractor.DatabaseExtractorFactory;

import org.junit.Test;

public class DatabaseExtractorFactoryTest {
    
    @Test
    public void testLoadConfiguration() {
        final DatabaseExtractorFactory factory = new DatabaseExtractorFactory("src/test/resources/dbextractor-ctx.xml");
        final DatabaseExtractor extractor = factory.getDatabaseExtractor();
        Assert.assertNotNull(extractor);
    }
    
    @Test
    public void testLoadConfigurationUsingFile() {
        final File file = new File("src/test/resources/dbextractor-ctx.xml");
        final DatabaseExtractorFactory factory = new DatabaseExtractorFactory(file.getPath());
        final DatabaseExtractor extractor = factory.getDatabaseExtractor();
        Assert.assertNotNull(extractor);
    }

}
