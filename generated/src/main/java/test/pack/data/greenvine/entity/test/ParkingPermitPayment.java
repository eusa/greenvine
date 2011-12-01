package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.util.Date;
import test.pack.data.greenvine.entity.test.ParkingPermitIdentity;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "test.ParkingPermitPayment")
@Table(name = "TEST.TBL_PARKING_PERMIT_PAYMENT")  
public class ParkingPermitPayment implements Comparable<ParkingPermitPayment>, Serializable {

    private static final long serialVersionUID = -3991951925095657966L;

    /**
    * Identity field
    */
    private ParkingPermitIdentity parkingPermitIdentity;

    /**
    * paymentDate field
    */
    private Date paymentDate;
    
    /**
    * parkingPermit constrained identity field
    */
    private ParkingPermit parkingPermit;
    
    /**
    * Default constructor
    */
    public ParkingPermitPayment() {
    }
    
    /**
    * Simple Property constructor
    */
    public ParkingPermitPayment(ParkingPermitIdentity parkingPermitIdentity, Date paymentDate) {
        this.parkingPermitIdentity = parkingPermitIdentity;
        this.paymentDate = paymentDate;
    }


    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
	@EmbeddedId
	@AttributeOverrides( {
        @AttributeOverride(name = "date", column = @Column(name = "FK_PARKING_PERMIT_DATE")), 
        @AttributeOverride(name = "regNumber", column = @Column(name = "FK_REG_NUMBER"))
    } )     
    public ParkingPermitIdentity getParkingPermitIdentity() {
        return this.parkingPermitIdentity;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setParkingPermitIdentity(ParkingPermitIdentity parkingPermitIdentity) {
        this.parkingPermitIdentity = parkingPermitIdentity;
    }

    /**
    * Accessor for paymentDate field
    * returns the value of the paymentDate field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "PAYMENT_DATE",  nullable = false, columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPaymentDate() {
        return this.paymentDate;
    }
          
    /**
    * Mutator for the paymentDate field
    * @param  sets the value of the paymentDate field
    */    
    public void setPaymentDate(Date paymentDate) {
      this.paymentDate = paymentDate;
    }
          
    /**
    * Accessor for parkingPermit field
    * @return the value of the parkingPermit field. 
    */
    @MapsId
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.test.ParkingPermit.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "FK_PARKING_PERMIT_DATE", referencedColumnName = "DATE",  nullable = false), 
        @JoinColumn(name = "FK_REG_NUMBER", referencedColumnName = "FK_REG_NUMBER",  nullable = false)    
    } )
    public ParkingPermit getParkingPermit() {
        return this.parkingPermit;
    }
      
    /**
    * Mutator for parkingPermit field
    * @param parkingPermit the new value for the parkingPermit field
    */    
    public void setParkingPermit(ParkingPermit parkingPermit) {
        this.parkingPermit = parkingPermit;
    }

    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Test.parkingPermitPayment
        if ( !(that instanceof ParkingPermitPayment) ) return false;

        // Safely cast to Test.parkingPermitPayment
        ParkingPermitPayment thatObj = (ParkingPermitPayment)that;

        // Equality is based on all field values
        return
            this.getPaymentDate() == null ? thatObj.getPaymentDate() == null : this.getPaymentDate().equals(thatObj.getPaymentDate())&&
            this.getParkingPermit() == null ? thatObj.getParkingPermit() == null : this.getParkingPermit().equals(thatObj.getParkingPermit())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getPaymentDate() ? 0 : getPaymentDate().hashCode());
        hash = 31 * hash + (null == getParkingPermit() ? 0 : getParkingPermit().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Test.parkingPermitPayment:";
        str +=  ("Identity = " + (null == parkingPermitIdentity ? "null" : parkingPermitIdentity.toString())) + ", ";
        str +=  ("paymentDate = " + (null == getPaymentDate() ? "null" : getPaymentDate().toString())) + ", ";
        str +=  ("parkingPermit = " + (null == getParkingPermit() ? "null" : getParkingPermit().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(ParkingPermitPayment thatObj) {
    
        int cmp;

        cmp = this.getPaymentDate() == null ?
                (thatObj.getPaymentDate() == null ? 0 : -1) :
                (thatObj.getPaymentDate() == null ? 1 : this.getPaymentDate().compareTo(thatObj.getPaymentDate())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getParkingPermit() == null ?
                (thatObj.getParkingPermit() == null ? 0 : -1) :
                (thatObj.getParkingPermit() == null ? 1 : this.getParkingPermit().compareTo(thatObj.getParkingPermit())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}