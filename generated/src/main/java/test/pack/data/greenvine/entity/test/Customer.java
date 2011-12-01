package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;
import test.pack.data.greenvine.entity.test.PersonIdentity;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "test.Customer")
@Table(name = "TEST.TBL_CUSTOMER")  
public class Customer implements Comparable<Customer>, Serializable {

    private static final long serialVersionUID = 3684855861126681998L;

    /**
    * Identity field
    */
    private PersonIdentity personIdentity;

    /**
    * loyaltyPoints field
    */
    private Integer loyaltyPoints;
    
    /**
    * person constrained identity field
    */
    private Person person;
    
    /**
    * consignments field
    */  
    private Collection<Consignment> consignments = new TreeSet<Consignment>();    

    /**
    * Default constructor
    */
    public Customer() {
    }
    
    /**
    * Simple Property constructor
    */
    public Customer(PersonIdentity personIdentity, Integer loyaltyPoints) {
        this.personIdentity = personIdentity;
        this.loyaltyPoints = loyaltyPoints;
    }

    /**
    * Full Property constructor
    */
    public Customer(PersonIdentity personIdentity, Person person, Integer loyaltyPoints, Collection<Consignment> consignments) {
        this.personIdentity = personIdentity;
        this.person = person;
        this.loyaltyPoints = loyaltyPoints;
        this.consignments = consignments;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
	@EmbeddedId
	@AttributeOverrides( {
        @AttributeOverride(name = "firstName", column = @Column(name = "FK_FIRST_NAME")), 
        @AttributeOverride(name = "lastName", column = @Column(name = "FK_LAST_NAME"))
    } )     
    public PersonIdentity getPersonIdentity() {
        return this.personIdentity;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setPersonIdentity(PersonIdentity personIdentity) {
        this.personIdentity = personIdentity;
    }

    /**
    * Accessor for loyaltyPoints field
    * returns the value of the loyaltyPoints field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "LOYALTY_POINTS",  nullable = false, columnDefinition = "INTEGER")
    public Integer getLoyaltyPoints() {
        return this.loyaltyPoints;
    }
          
    /**
    * Mutator for the loyaltyPoints field
    * @param  sets the value of the loyaltyPoints field
    */    
    public void setLoyaltyPoints(Integer loyaltyPoints) {
      this.loyaltyPoints = loyaltyPoints;
    }
          
    /**
    * Accessor for person field
    * @return the value of the person field. 
    */
    @MapsId
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.test.Person.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "FK_FIRST_NAME", referencedColumnName = "FIRST_NAME",  nullable = false), 
        @JoinColumn(name = "FK_LAST_NAME", referencedColumnName = "LAST_NAME",  nullable = false)    
    } )
    public Person getPerson() {
        return this.person;
    }
      
    /**
    * Mutator for person field
    * @param person the new value for the person field
    */    
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
    * Accessor for consignments field
    * @return the value of the consignments field. 
    */
    @OneToMany(targetEntity = test.pack.data.greenvine.entity.test.Consignment.class, mappedBy = "consignmentNaturalIdentity.customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
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

        // Check candidate is an instance of Test.customer
        if ( !(that instanceof Customer) ) return false;

        // Safely cast to Test.customer
        Customer thatObj = (Customer)that;

        // Equality is based on all field values
        return
            this.getLoyaltyPoints() == null ? thatObj.getLoyaltyPoints() == null : this.getLoyaltyPoints().equals(thatObj.getLoyaltyPoints())&&
            this.getPerson() == null ? thatObj.getPerson() == null : this.getPerson().equals(thatObj.getPerson())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getLoyaltyPoints() ? 0 : getLoyaltyPoints().hashCode());
        hash = 31 * hash + (null == getPerson() ? 0 : getPerson().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Test.customer:";
        str +=  ("Identity = " + (null == personIdentity ? "null" : personIdentity.toString())) + ", ";
        str +=  ("loyaltyPoints = " + (null == getLoyaltyPoints() ? "null" : getLoyaltyPoints().toString())) + ", ";
        str +=  ("person = " + (null == getPerson() ? "null" : getPerson().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Customer thatObj) {
    
        int cmp;

        cmp = this.getLoyaltyPoints() == null ?
                (thatObj.getLoyaltyPoints() == null ? 0 : -1) :
                (thatObj.getLoyaltyPoints() == null ? 1 : this.getLoyaltyPoints().compareTo(thatObj.getLoyaltyPoints())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getPerson() == null ?
                (thatObj.getPerson() == null ? 0 : -1) :
                (thatObj.getPerson() == null ? 1 : this.getPerson().compareTo(thatObj.getPerson())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}