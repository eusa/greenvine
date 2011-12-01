package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityComponentIdentityGenerator")
@Embeddable    
public class ConsignmentNaturalIdentity implements Comparable<ConsignmentNaturalIdentity>, Serializable {

private static final long serialVersionUID = -8388605899999898375L;

    /**
    * consignmentDate property
    */
    private Date consignmentDate;

    /**
    * customer property
    */
    private Customer customer;

    /**
    * Accessor for consignmentDate field
    * returns the value of the consignmentDate field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "CONSIGNMENT_DATE",  nullable = false, columnDefinition = "DATE")
    public Date getConsignmentDate() {
        return this.consignmentDate;
    }
              
    /**
    * Mutator for the consignmentDate field
    * @param  sets the value of the consignmentDate field
    */    
    public void setConsignmentDate(Date consignmentDate) {
        this.consignmentDate = consignmentDate;
    }    

    /**
    * Accessor for customer field
    * returns the value of the customer field
    */
    @ManyToOne(targetEntity = test.pack.data.greenvine.entity.test.Customer.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "FK_FIRST_NAME", referencedColumnName = "FK_FIRST_NAME",  nullable = false), 
        @JoinColumn(name = "FK_LAST_NAME", referencedColumnName = "FK_LAST_NAME",  nullable = false)
    } )
    public Customer getCustomer() {
        return this.customer;
    }
              
    /**
    * Mutator for the customer field
    * @param  sets the value of the customer field
    */    
    public void setCustomer(test.pack.data.greenvine.entity.test.Customer customer) {
        this.customer = customer;
    }    

    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of ConsignmentNaturalIdentity
        if ( !(that instanceof ConsignmentNaturalIdentity) ) return false;

        // Safely cast to ConsignmentNaturalIdentity
        ConsignmentNaturalIdentity thatObj = (ConsignmentNaturalIdentity)that;

        // Equality is based on all property values
        return
            this.getConsignmentDate() == null ? thatObj.getConsignmentDate() == null : this.getConsignmentDate().equals(thatObj.getConsignmentDate())&&
            this.getCustomer() == null ? thatObj.getCustomer() == null : this.getCustomer().equals(thatObj.getCustomer())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all properties
        hash = 31 * hash + (null == getConsignmentDate() ? 0 : getConsignmentDate().hashCode());
        hash = 31 * hash + (null == getCustomer() ? 0 : getCustomer().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "ConsignmentNaturalIdentity:";
        str +=  ("consignmentDate = " + (null == getConsignmentDate() ? "null" : getConsignmentDate().toString())) + ", ";
        str +=  ("customer = " + (null == getCustomer() ? "null" : getCustomer().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(ConsignmentNaturalIdentity thatObj) {
    
        int cmp;

        cmp = this.getConsignmentDate() == null ?
                (thatObj.getConsignmentDate() == null ? 0 : -1) :
                (thatObj.getConsignmentDate() == null ? 1 : this.getConsignmentDate().compareTo(thatObj.getConsignmentDate())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getCustomer() == null ?
                (thatObj.getCustomer() == null ? 0 : -1) :
                (thatObj.getCustomer() == null ? 1 : this.getCustomer().compareTo(thatObj.getCustomer())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }   
}