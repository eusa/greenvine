package net.sourceforge.greenvine.generator.helper;

public interface JdbcType {
	
	public Boolean getEnquoteLiterals();
    
    public String getCreateData();
    
    public String getUpdateData();
    
    public String getRandomData();

}
