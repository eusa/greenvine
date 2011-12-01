package test.pack.data.greenvine.entity.test;

import java.io.Serializable;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityComponentIdentityGenerator")
@Embeddable    
public class AddressNaturalIdentity implements Comparable<AddressNaturalIdentity>, Serializable {

private static final long serialVersionUID = 5601446077788815013L;

    /**
    * houseNumber property
    */
    private String houseNumber;

    /**
    * streetName property
    */
    private String streetName;

    /**
    * Accessor for houseNumber field
    * returns the value of the houseNumber field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "HOUSE_NUMBER",  nullable = false, columnDefinition = "CHARACTER(5)")
    public String getHouseNumber() {
        return this.houseNumber;
    }
              
    /**
    * Mutator for the houseNumber field
    * @param  sets the value of the houseNumber field
    */    
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }    

    /**
    * Accessor for streetName field
    * returns the value of the streetName field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "STREET_NAME",  nullable = false, columnDefinition = "VARCHAR(100)")
    public String getStreetName() {
        return this.streetName;
    }
              
    /**
    * Mutator for the streetName field
    * @param  sets the value of the streetName field
    */    
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }    

    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of AddressNaturalIdentity
        if ( !(that instanceof AddressNaturalIdentity) ) return false;

        // Safely cast to AddressNaturalIdentity
        AddressNaturalIdentity thatObj = (AddressNaturalIdentity)that;

        // Equality is based on all property values
        return
            this.getHouseNumber() == null ? thatObj.getHouseNumber() == null : this.getHouseNumber().equals(thatObj.getHouseNumber())&&
            this.getStreetName() == null ? thatObj.getStreetName() == null : this.getStreetName().equals(thatObj.getStreetName())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all properties
        hash = 31 * hash + (null == getHouseNumber() ? 0 : getHouseNumber().hashCode());
        hash = 31 * hash + (null == getStreetName() ? 0 : getStreetName().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "AddressNaturalIdentity:";
        str +=  ("houseNumber = " + (null == getHouseNumber() ? "null" : getHouseNumber().toString())) + ", ";
        str +=  ("streetName = " + (null == getStreetName() ? "null" : getStreetName().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(AddressNaturalIdentity thatObj) {
    
        int cmp;

        cmp = this.getHouseNumber() == null ?
                (thatObj.getHouseNumber() == null ? 0 : -1) :
                (thatObj.getHouseNumber() == null ? 1 : this.getHouseNumber().compareTo(thatObj.getHouseNumber())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getStreetName() == null ?
                (thatObj.getStreetName() == null ? 0 : -1) :
                (thatObj.getStreetName() == null ? 1 : this.getStreetName().compareTo(thatObj.getStreetName())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }   
}