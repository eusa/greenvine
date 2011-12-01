package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.util.Date;
import test.pack.data.greenvine.entity.test.PersonIdentity;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "test.Person")
@Table(name = "TEST.TBL_PERSON")  
public class Person implements Comparable<Person>, Serializable {

    private static final long serialVersionUID = 8615812077316541630L;

    /**
    * Identity field
    */
    private PersonIdentity personIdentity;

    /**
    * birthday field
    */
    private Date birthday;
    
    /**
    * customer field
    */
    private Customer customer;    

    /**
    * passport field
    */
    private Passport passport;    

    /**
    * Default constructor
    */
    public Person() {
    }
    
    /**
    * Simple Property constructor
    */
    public Person(PersonIdentity personIdentity, Date birthday) {
        this.personIdentity = personIdentity;
        this.birthday = birthday;
    }

    /**
    * Full Property constructor
    */
    public Person(PersonIdentity personIdentity, Date birthday, Customer customer, Passport passport) {
        this.personIdentity = personIdentity;
        this.birthday = birthday;
        this.customer = customer;
        this.passport = passport;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
	@EmbeddedId
	@AttributeOverrides( {
        @AttributeOverride(name = "firstName", column = @Column(name = "FIRST_NAME")), 
        @AttributeOverride(name = "lastName", column = @Column(name = "LAST_NAME"))
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
    * Accessor for birthday field
    * returns the value of the birthday field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "BIRTHDAY",  nullable = false, columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    public Date getBirthday() {
        return this.birthday;
    }
          
    /**
    * Mutator for the birthday field
    * @param  sets the value of the birthday field
    */    
    public void setBirthday(Date birthday) {
      this.birthday = birthday;
    }
          
    /**
    * Accessor for customer field
    * @return the value of the customer field. 
    */
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.test.Customer.class, fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "person")
    public Customer getCustomer() {
        return this.customer;
    }
      
    /**
    * Mutator for customer field
    * @param customer the new value for the customer field
    */    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
          
    /**
    * Accessor for passport field
    * @return the value of the passport field. 
    */
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.test.Passport.class, fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "person")
    public Passport getPassport() {
        return this.passport;
    }
      
    /**
    * Mutator for passport field
    * @param passport the new value for the passport field
    */    
    public void setPassport(Passport passport) {
        this.passport = passport;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Test.person
        if ( !(that instanceof Person) ) return false;

        // Safely cast to Test.person
        Person thatObj = (Person)that;

        // Equality is based on all field values
        return
            this.getBirthday() == null ? thatObj.getBirthday() == null : this.getBirthday().equals(thatObj.getBirthday())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getBirthday() ? 0 : getBirthday().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Test.person:";
        str +=  ("Identity = " + (null == personIdentity ? "null" : personIdentity.toString())) + ", ";
        str +=  ("birthday = " + (null == getBirthday() ? "null" : getBirthday().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Person thatObj) {
    
        int cmp;

        cmp = this.getBirthday() == null ?
                (thatObj.getBirthday() == null ? 0 : -1) :
                (thatObj.getBirthday() == null ? 1 : this.getBirthday().compareTo(thatObj.getBirthday())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}