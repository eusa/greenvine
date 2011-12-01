package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.lang.Integer;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "test.Consignment")
@Table(name = "TEST.TBL_CONSIGNMENT", uniqueConstraints = { @UniqueConstraint(columnNames = { "CONSIGNMENT_DATE", "FK_FIRST_NAME", "FK_LAST_NAME" }) })  
public class Consignment implements Comparable<Consignment>, Serializable {

    private static final long serialVersionUID = -6484787477339692690L;

    /**
    * Identity field
    */
    private Integer consignmentId;

    /**
    * NaturalIdentity field
    */
    private ConsignmentNaturalIdentity consignmentNaturalIdentity;

    /**
    * address field
    */
    private Address address;    

    /**
    * Default constructor
    */
    public Consignment() {
    }
    
    /**
    * Simple Property constructor
    */
    public Consignment(Integer consignmentId, ConsignmentNaturalIdentity consignmentNaturalIdentity) {
        this.consignmentId = consignmentId;
        this.consignmentNaturalIdentity = consignmentNaturalIdentity;
    }

    /**
    * Full Property constructor
    */
    public Consignment(Integer consignmentId, ConsignmentNaturalIdentity consignmentNaturalIdentity, Address address) {
        this.consignmentId = consignmentId;
        this.consignmentNaturalIdentity = consignmentNaturalIdentity;
        this.address = address;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "CONSIGNMENT_ID", nullable = false)
    public Integer getConsignmentId() {
        return this.consignmentId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setConsignmentId(Integer consignmentId) {
        this.consignmentId = consignmentId;
    }

    /**
    * Accessor for the natural identity field consignmentNaturalIdentity
    * @returns the value of the field consignmentNaturalIdentity
    */
            // @NaturalId 
	@Embedded
	@AttributeOverrides( {
        @AttributeOverride(name = "consignmentDate", column = @Column(name = "CONSIGNMENT_DATE"))
    } )	    
	@AssociationOverrides( {
        @AssociationOverride(name = "customer", 
            joinColumns = {
                @JoinColumn(name = "FK_FIRST_NAME", referencedColumnName = "FK_FIRST_NAME",  nullable = false), 
                @JoinColumn(name = "FK_LAST_NAME", referencedColumnName = "FK_LAST_NAME",  nullable = false)
             }
         )
    } )	    
    public ConsignmentNaturalIdentity getConsignmentNaturalIdentity() {
        return this.consignmentNaturalIdentity;
    }
    
    /**
    * Mutator for the natural identity field consignmentNaturalIdentity
    * @param sets the value of the consignmentNaturalIdentity field
    */
    public void setConsignmentNaturalIdentity(ConsignmentNaturalIdentity consignmentNaturalIdentity) {
        this.consignmentNaturalIdentity = consignmentNaturalIdentity;
    }

    /**
    * Accessor for address field
    * @return the value of the address field. 
    */
    @ManyToOne(targetEntity = test.pack.data.greenvine.entity.test.Address.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "FK_ADDRESS_ID", referencedColumnName = "ADDRESS_ID",  nullable = false)
    } )
    public Address getAddress() {
        return this.address;
    }
      
    /**
    * Mutator for address field
    * @param address the new value for the address field
    */    
    public void setAddress(Address address) {
        this.address = address;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Test.consignment
        if ( !(that instanceof Consignment) ) return false;

        // Safely cast to Test.consignment
        Consignment thatObj = (Consignment)that;

        // Equality is based on natural id
        return
            this.getConsignmentNaturalIdentity() == null ? thatObj.getConsignmentNaturalIdentity() == null : this.getConsignmentNaturalIdentity().equals(thatObj.getConsignmentNaturalIdentity()) && 
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on natural id
        hash = 31 * hash + (null == getConsignmentNaturalIdentity() ? 0 : getConsignmentNaturalIdentity().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Test.consignment:";
        str +=  ("Identity = " + (null == consignmentId ? "null" : consignmentId.toString())) + ", ";
        str +=  ("consignmentNaturalIdentity = " + (null == getConsignmentNaturalIdentity() ? "null" : getConsignmentNaturalIdentity().toString())) + ", ";
        str +=  ("address = " + (null == getAddress() ? "null" : getAddress().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Consignment thatObj) {
    
        int cmp;

        cmp = this.getConsignmentNaturalIdentity().compareTo(thatObj.getConsignmentNaturalIdentity());
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}