package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;
import java.lang.Integer;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "test.Address")
@Table(name = "TEST.TBL_ADDRESS", uniqueConstraints = { @UniqueConstraint(columnNames = { "HOUSE_NUMBER", "STREET_NAME" }) })  
public class Address implements Comparable<Address>, Serializable {

    private static final long serialVersionUID = 6783492125450923048L;

    /**
    * Identity field
    */
    private Integer addressId;

    /**
    * NaturalIdentity field
    */
    private AddressNaturalIdentity addressNaturalIdentity;

    /**
    * postCode field
    */
    private String postCode;
    
    /**
    * consignments field
    */  
    private Collection<Consignment> consignments = new TreeSet<Consignment>();    

    /**
    * Default constructor
    */
    public Address() {
    }
    
    /**
    * Simple Property constructor
    */
    public Address(Integer addressId, AddressNaturalIdentity addressNaturalIdentity, String postCode) {
        this.addressId = addressId;
        this.addressNaturalIdentity = addressNaturalIdentity;
        this.postCode = postCode;
    }

    /**
    * Full Property constructor
    */
    public Address(Integer addressId, AddressNaturalIdentity addressNaturalIdentity, String postCode, Collection<Consignment> consignments) {
        this.addressId = addressId;
        this.addressNaturalIdentity = addressNaturalIdentity;
        this.postCode = postCode;
        this.consignments = consignments;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "ADDRESS_ID", nullable = false)
    public Integer getAddressId() {
        return this.addressId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    /**
    * Accessor for the natural identity field addressNaturalIdentity
    * @returns the value of the field addressNaturalIdentity
    */
            // @NaturalId 
	@Embedded
	@AttributeOverrides( {
        @AttributeOverride(name = "houseNumber", column = @Column(name = "HOUSE_NUMBER")), 
        @AttributeOverride(name = "streetName", column = @Column(name = "STREET_NAME"))
    } )	    
    public AddressNaturalIdentity getAddressNaturalIdentity() {
        return this.addressNaturalIdentity;
    }
    
    /**
    * Mutator for the natural identity field addressNaturalIdentity
    * @param sets the value of the addressNaturalIdentity field
    */
    public void setAddressNaturalIdentity(AddressNaturalIdentity addressNaturalIdentity) {
        this.addressNaturalIdentity = addressNaturalIdentity;
    }

    /**
    * Accessor for postCode field
    * returns the value of the postCode field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "POST_CODE",  nullable = false, columnDefinition = "CHARACTER(10)")
    public String getPostCode() {
        return this.postCode;
    }
          
    /**
    * Mutator for the postCode field
    * @param  sets the value of the postCode field
    */    
    public void setPostCode(String postCode) {
      this.postCode = postCode;
    }
          
    /**
    * Accessor for consignments field
    * @return the value of the consignments field. 
    */
    @OneToMany(targetEntity = test.pack.data.greenvine.entity.test.Consignment.class, mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Collection<Consignment> getConsignments() {
        return this.consignments;
    }
      
    /**
    * Mutator for consignments field
    * @param consignments the new value for the consignments field
    */        
    public void setConsignments(Collection<Consignment> consignments) {
        this.consignments = consignments;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Test.address
        if ( !(that instanceof Address) ) return false;

        // Safely cast to Test.address
        Address thatObj = (Address)that;

        // Equality is based on natural id
        return
            this.getAddressNaturalIdentity() == null ? thatObj.getAddressNaturalIdentity() == null : this.getAddressNaturalIdentity().equals(thatObj.getAddressNaturalIdentity()) && 
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on natural id
        hash = 31 * hash + (null == getAddressNaturalIdentity() ? 0 : getAddressNaturalIdentity().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Test.address:";
        str +=  ("Identity = " + (null == addressId ? "null" : addressId.toString())) + ", ";
        str +=  ("postCode = " + (null == getPostCode() ? "null" : getPostCode().toString())) + ", ";
        str +=  ("addressNaturalIdentity = " + (null == getAddressNaturalIdentity() ? "null" : getAddressNaturalIdentity().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Address thatObj) {
    
        int cmp;

        cmp = this.getAddressNaturalIdentity().compareTo(thatObj.getAddressNaturalIdentity());
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}