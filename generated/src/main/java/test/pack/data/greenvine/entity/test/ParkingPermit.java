package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.math.BigDecimal;
import test.pack.data.greenvine.entity.test.ParkingPermitIdentity;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "test.ParkingPermit")
@Table(name = "TEST.TBL_PARKING_PERMIT")  
public class ParkingPermit implements Comparable<ParkingPermit>, Serializable {

    private static final long serialVersionUID = -7357733605985971338L;

    /**
    * Identity field
    */
    private ParkingPermitIdentity parkingPermitIdentity;

    /**
    * value field
    */
    private BigDecimal value;
    
    /**
    * parkingPermitPayment field
    */
    private ParkingPermitPayment parkingPermitPayment;    

    /**
    * vehicle field
    */
    private Vehicle vehicle;    

    /**
    * Default constructor
    */
    public ParkingPermit() {
    }
    
    /**
    * Simple Property constructor
    */
    public ParkingPermit(ParkingPermitIdentity parkingPermitIdentity, BigDecimal value) {
        this.parkingPermitIdentity = parkingPermitIdentity;
        this.value = value;
    }

    /**
    * Full Property constructor
    */
    public ParkingPermit(ParkingPermitIdentity parkingPermitIdentity, BigDecimal value, ParkingPermitPayment parkingPermitPayment, Vehicle vehicle) {
        this.parkingPermitIdentity = parkingPermitIdentity;
        this.value = value;
        this.parkingPermitPayment = parkingPermitPayment;
        this.vehicle = vehicle;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
	@EmbeddedId
	@AttributeOverrides( {
        @AttributeOverride(name = "date", column = @Column(name = "DATE")), 
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
    * Accessor for value field
    * returns the value of the value field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "VALUE",  nullable = false, columnDefinition = "DECIMAL(4, 1)")
    public BigDecimal getValue() {
        return this.value;
    }
          
    /**
    * Mutator for the value field
    * @param  sets the value of the value field
    */    
    public void setValue(BigDecimal value) {
      this.value = value;
    }
          
    /**
    * Accessor for parkingPermitPayment field
    * @return the value of the parkingPermitPayment field. 
    */
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.test.ParkingPermitPayment.class, fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "parkingPermit")
    public ParkingPermitPayment getParkingPermitPayment() {
        return this.parkingPermitPayment;
    }
      
    /**
    * Mutator for parkingPermitPayment field
    * @param parkingPermitPayment the new value for the parkingPermitPayment field
    */    
    public void setParkingPermitPayment(ParkingPermitPayment parkingPermitPayment) {
        this.parkingPermitPayment = parkingPermitPayment;
    }
          
    /**
    * Accessor for vehicle field
    * @return the value of the vehicle field. 
    */
    @MapsId("regNumber")
    @ManyToOne(targetEntity = test.pack.data.greenvine.entity.test.Vehicle.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "FK_REG_NUMBER", referencedColumnName = "REG_NUMBER",  nullable = false, insertable = false, updatable = false)
    } )
    public Vehicle getVehicle() {
        return this.vehicle;
    }
      
    /**
    * Mutator for vehicle field
    * @param vehicle the new value for the vehicle field
    */    
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Test.parkingPermit
        if ( !(that instanceof ParkingPermit) ) return false;

        // Safely cast to Test.parkingPermit
        ParkingPermit thatObj = (ParkingPermit)that;

        // Equality is based on all field values
        return
            this.getValue() == null ? thatObj.getValue() == null : this.getValue().equals(thatObj.getValue())&&
            this.getVehicle() == null ? thatObj.getVehicle() == null : this.getVehicle().equals(thatObj.getVehicle())&&        
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getValue() ? 0 : getValue().hashCode());
        hash = 31 * hash + (null == getVehicle() ? 0 : getVehicle().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Test.parkingPermit:";
        str +=  ("Identity = " + (null == parkingPermitIdentity ? "null" : parkingPermitIdentity.toString())) + ", ";
        str +=  ("value = " + (null == getValue() ? "null" : getValue().toString())) + ", ";
        str +=  ("vehicle = " + (null == getVehicle() ? "null" : getVehicle().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(ParkingPermit thatObj) {
    
        int cmp;

        cmp = this.getValue() == null ?
                (thatObj.getValue() == null ? 0 : -1) :
                (thatObj.getValue() == null ? 1 : this.getValue().compareTo(thatObj.getValue())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getVehicle() == null ?
                (thatObj.getVehicle() == null ? 0 : -1) :
                (thatObj.getVehicle() == null ? 1 : this.getVehicle().compareTo(thatObj.getVehicle())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}