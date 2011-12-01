package net.sourceforge.greenvine.model.api;

public interface OneToOneChildIdentity extends OneToOneChildField, Identity {

    Identity getUltimateNonConstrainedIdentity();

}