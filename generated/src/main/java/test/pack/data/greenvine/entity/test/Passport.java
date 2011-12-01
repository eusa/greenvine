package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "test.Passport")
@Table(name = "TEST.TBL_PASSPORT", uniqueConstraints = { @UniqueConstraint(columnNames = { "FK_FIRST_NAME", "FK_LAST_NAME" }) })  
public class Passport implements Comparable<Passport>, Serializable {

    private static final long serialVersionUID = -2803862657841001025L;

    /**
    * Identity field
    */
    private String passportNr;

    /**
    * NaturalIdentity field
    */
    private Person person;

    /**
    * expiryDate field
    */
    private Date expiryDate;
    
    /**
    * Default constructor
    */
    public Passport() {
    }
    
    /**
    * Simple Property constructor
    */
    public Passport(String passportNr, Person person, Date expiryDate) {
        this.passportNr = passportNr;
        this.person = person;
        this.expiryDate = expiryDate;
    }


    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "PASSPORT_NR", nullable = false)
    public String getPassportNr() {
        return this.passportNr;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setPassportNr(String passportNr) {
        this.passportNr = passportNr;
    }

    /**
    * Accessor for the natural identity field person
    * @returns the value of the field person
    */
            // @NaturalId 
    /**
    * Accessor for person field
    * @return the value of the person field. 
    */
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.test.Person.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "FK_FIRST_NAME", referencedColumnName = "FIRST_NAME",  nullable = false), 
        @JoinColumn(name = "FK_LAST_NAME", referencedColumnName = "LAST_NAME",  nullable = false)    
    } )
    public Person getPerson() {
        return this.person;
    }
    
    /**
    * Mutator for the natural identity field person
    * @param sets the value of the person field
    */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
    * Accessor for expiryDate field
    * returns the value of the expiryDate field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "EXPIRY_DATE",  nullable = false, columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    public Date getExpiryDate() {
        return this.expiryDate;
    }
          
    /**
    * Mutator for the expiryDate field
    * @param  sets the value of the expiryDate field
    */    
    public void setExpiryDate(Date expiryDate) {
      this.expiryDate = expiryDate;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Test.passport
        if ( !(that instanceof Passport) ) return false;

        // Safely cast to Test.passport
        Passport thatObj = (Passport)that;

        // Equality is based on natural id
        return
            this.getPerson() == null ? thatObj.getPerson() == null : this.getPerson().equals(thatObj.getPerson()) && 
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on natural id
        hash = 31 * hash + (null == getPerson() ? 0 : getPerson().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Test.passport:";
        str +=  ("Identity = " + (null == passportNr ? "null" : passportNr.toString())) + ", ";
        str +=  ("expiryDate = " + (null == getExpiryDate() ? "null" : getExpiryDate().toString())) + ", ";
        str +=  ("person = " + (null == getPerson() ? "null" : getPerson().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Passport thatObj) {
    
        int cmp;

        cmp = this.getPerson().compareTo(thatObj.getPerson());
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}