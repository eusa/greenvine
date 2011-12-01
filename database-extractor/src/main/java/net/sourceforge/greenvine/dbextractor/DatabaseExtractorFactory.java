package net.sourceforge.greenvine.dbextractor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class DatabaseExtractorFactory {
    
    private final DatabaseExtractor databaseExtractor;

    public DatabaseExtractorFactory(String path) {
        
        // Set up Spring context
        ApplicationContext context = new FileSystemXmlApplicationContext(
                new String[] {path});
        
        // Get the DatabaseExtractor
        this.databaseExtractor = (DatabaseExtractor)context.getBean("databaseExtractor");
        
    }

    public DatabaseExtractor getDatabaseExtractor() {
        return databaseExtractor;
    }

}
