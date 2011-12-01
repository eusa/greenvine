package net.sourceforge.greenvine.model.api;

public interface SimpleIdentity extends ColumnField, Identity {

    public abstract PropertyValueGenerationStrategy getKeyGenerationStrategy();

    public abstract PrimaryKey getPrimaryKey();

}