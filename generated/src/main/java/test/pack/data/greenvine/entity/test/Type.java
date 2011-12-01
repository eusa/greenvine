package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.lang.Long;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "test.Type")
@Table(name = "TEST.TBL_TYPES")  
public class Type implements Comparable<Type>, Serializable {

    private static final long serialVersionUID = -3056890396060265678L;

    /**
    * Identity field
    */
    private Long type6;

    /**
    * type1 field
    */
    private Integer type1;
    
    /**
    * type10 field
    */
    private Date type10;
    
    /**
    * type12 field
    */
    private Date type12;
    
    /**
    * type13 field
    */
    private Date type13;
    
    /**
    * type14 field
    */
    private byte[] type14;
    
    /**
    * type16 field
    */
    private String type16;
    
    /**
    * type17 field
    */
    private String type17;
    
    /**
    * type18 field
    */
    private byte[] type18;
    
    /**
    * type19 field
    */
    private String type19;
    
    /**
    * type2 field
    */
    private Boolean type2;
    
    /**
    * type20 field
    */
    private String type20;
    
    /**
    * type3 field
    */
    private Byte type3;
    
    /**
    * type4 field
    */
    private Short type4;
    
    /**
    * type5 field
    */
    private Long type5;
    
    /**
    * type7 field
    */
    private BigDecimal type7;
    
    /**
    * type8 field
    */
    private Double type8;
    
    /**
    * type9 field
    */
    private Float type9;
    
    /**
    * Default constructor
    */
    public Type() {
    }
    
    /**
    * Simple Property constructor
    */
    public Type(Long type6, Integer type1, Date type10, Date type12, Date type13, byte[] type14, String type16, String type17, byte[] type18, String type19, Boolean type2, String type20, Byte type3, Short type4, Long type5, BigDecimal type7, Double type8, Float type9) {
        this.type6 = type6;
        this.type1 = type1;
        this.type10 = type10;
        this.type12 = type12;
        this.type13 = type13;
        this.type14 = type14;
        this.type16 = type16;
        this.type17 = type17;
        this.type18 = type18;
        this.type19 = type19;
        this.type2 = type2;
        this.type20 = type20;
        this.type3 = type3;
        this.type4 = type4;
        this.type5 = type5;
        this.type7 = type7;
        this.type8 = type8;
        this.type9 = type9;
    }


    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "TYPE_6", nullable = false)
    public Long getType6() {
        return this.type6;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setType6(Long type6) {
        this.type6 = type6;
    }

    /**
    * Accessor for type1 field
    * returns the value of the type1 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_1",  nullable = false, columnDefinition = "INTEGER")
    public Integer getType1() {
        return this.type1;
    }
          
    /**
    * Mutator for the type1 field
    * @param  sets the value of the type1 field
    */    
    public void setType1(Integer type1) {
      this.type1 = type1;
    }
          
    /**
    * Accessor for type10 field
    * returns the value of the type10 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_10",  nullable = false, columnDefinition = "TIME")
    @Temporal(TemporalType.TIME)
    public Date getType10() {
        return this.type10;
    }
          
    /**
    * Mutator for the type10 field
    * @param  sets the value of the type10 field
    */    
    public void setType10(Date type10) {
      this.type10 = type10;
    }
          
    /**
    * Accessor for type12 field
    * returns the value of the type12 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_12",  nullable = false, columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    public Date getType12() {
        return this.type12;
    }
          
    /**
    * Mutator for the type12 field
    * @param  sets the value of the type12 field
    */    
    public void setType12(Date type12) {
      this.type12 = type12;
    }
          
    /**
    * Accessor for type13 field
    * returns the value of the type13 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_13",  nullable = false, columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getType13() {
        return this.type13;
    }
          
    /**
    * Mutator for the type13 field
    * @param  sets the value of the type13 field
    */    
    public void setType13(Date type13) {
      this.type13 = type13;
    }
          
    /**
    * Accessor for type14 field
    * returns the value of the type14 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_14",  nullable = false, columnDefinition = "VARBINARY")
    public byte[] getType14() {
        return this.type14;
    }
          
    /**
    * Mutator for the type14 field
    * @param  sets the value of the type14 field
    */    
    public void setType14(byte[] type14) {
      this.type14 = type14;
    }
          
    /**
    * Accessor for type16 field
    * returns the value of the type16 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_16",  nullable = false, columnDefinition = "VARCHAR(20)")
    public String getType16() {
        return this.type16;
    }
          
    /**
    * Mutator for the type16 field
    * @param  sets the value of the type16 field
    */    
    public void setType16(String type16) {
      this.type16 = type16;
    }
          
    /**
    * Accessor for type17 field
    * returns the value of the type17 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_17",  nullable = false, columnDefinition = "CHARACTER(20)")
    public String getType17() {
        return this.type17;
    }
          
    /**
    * Mutator for the type17 field
    * @param  sets the value of the type17 field
    */    
    public void setType17(String type17) {
      this.type17 = type17;
    }
          
    /**
    * Accessor for type18 field
    * returns the value of the type18 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_18",  nullable = false, columnDefinition = "BLOB")
    public byte[] getType18() {
        return this.type18;
    }
          
    /**
    * Mutator for the type18 field
    * @param  sets the value of the type18 field
    */    
    public void setType18(byte[] type18) {
      this.type18 = type18;
    }
          
    /**
    * Accessor for type19 field
    * returns the value of the type19 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_19",  nullable = false, columnDefinition = "CLOB")
    @Lob
    public String getType19() {
        return this.type19;
    }
          
    /**
    * Mutator for the type19 field
    * @param  sets the value of the type19 field
    */    
    public void setType19(String type19) {
      this.type19 = type19;
    }
          
    /**
    * Accessor for type2 field
    * returns the value of the type2 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_2",  nullable = false, columnDefinition = "BOOLEAN")
    public Boolean getType2() {
        return this.type2;
    }
          
    /**
    * Mutator for the type2 field
    * @param  sets the value of the type2 field
    */    
    public void setType2(Boolean type2) {
      this.type2 = type2;
    }
          
    /**
    * Accessor for type20 field
    * returns the value of the type20 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_20",  nullable = false, columnDefinition = "CHARACTER(20)")
    public String getType20() {
        return this.type20;
    }
          
    /**
    * Mutator for the type20 field
    * @param  sets the value of the type20 field
    */    
    public void setType20(String type20) {
      this.type20 = type20;
    }
          
    /**
    * Accessor for type3 field
    * returns the value of the type3 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_3",  nullable = false, columnDefinition = "TINYINT")
    public Byte getType3() {
        return this.type3;
    }
          
    /**
    * Mutator for the type3 field
    * @param  sets the value of the type3 field
    */    
    public void setType3(Byte type3) {
      this.type3 = type3;
    }
          
    /**
    * Accessor for type4 field
    * returns the value of the type4 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_4",  nullable = false, columnDefinition = "SMALLINT")
    public Short getType4() {
        return this.type4;
    }
          
    /**
    * Mutator for the type4 field
    * @param  sets the value of the type4 field
    */    
    public void setType4(Short type4) {
      this.type4 = type4;
    }
          
    /**
    * Accessor for type5 field
    * returns the value of the type5 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_5",  nullable = false, columnDefinition = "BIGINT")
    public Long getType5() {
        return this.type5;
    }
          
    /**
    * Mutator for the type5 field
    * @param  sets the value of the type5 field
    */    
    public void setType5(Long type5) {
      this.type5 = type5;
    }
          
    /**
    * Accessor for type7 field
    * returns the value of the type7 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_7",  nullable = false, columnDefinition = "DECIMAL(10, 2)")
    public BigDecimal getType7() {
        return this.type7;
    }
          
    /**
    * Mutator for the type7 field
    * @param  sets the value of the type7 field
    */    
    public void setType7(BigDecimal type7) {
      this.type7 = type7;
    }
          
    /**
    * Accessor for type8 field
    * returns the value of the type8 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_8",  nullable = false, columnDefinition = "DOUBLE(17)")
    public Double getType8() {
        return this.type8;
    }
          
    /**
    * Mutator for the type8 field
    * @param  sets the value of the type8 field
    */    
    public void setType8(Double type8) {
      this.type8 = type8;
    }
          
    /**
    * Accessor for type9 field
    * returns the value of the type9 field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TYPE_9",  nullable = false, columnDefinition = "REAL(7)")
    public Float getType9() {
        return this.type9;
    }
          
    /**
    * Mutator for the type9 field
    * @param  sets the value of the type9 field
    */    
    public void setType9(Float type9) {
      this.type9 = type9;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Test.type
        if ( !(that instanceof Type) ) return false;

        // Safely cast to Test.type
        Type thatObj = (Type)that;

        // Equality is based on all field values
        return
            this.getType1() == null ? thatObj.getType1() == null : this.getType1().equals(thatObj.getType1())&&
            this.getType10() == null ? thatObj.getType10() == null : this.getType10().equals(thatObj.getType10())&&
            this.getType12() == null ? thatObj.getType12() == null : this.getType12().equals(thatObj.getType12())&&
            this.getType13() == null ? thatObj.getType13() == null : this.getType13().equals(thatObj.getType13())&&
            this.getType16() == null ? thatObj.getType16() == null : this.getType16().equals(thatObj.getType16())&&
            this.getType17() == null ? thatObj.getType17() == null : this.getType17().equals(thatObj.getType17())&&
            this.getType19() == null ? thatObj.getType19() == null : this.getType19().equals(thatObj.getType19())&&
            this.getType2() == null ? thatObj.getType2() == null : this.getType2().equals(thatObj.getType2())&&
            this.getType20() == null ? thatObj.getType20() == null : this.getType20().equals(thatObj.getType20())&&
            this.getType3() == null ? thatObj.getType3() == null : this.getType3().equals(thatObj.getType3())&&
            this.getType4() == null ? thatObj.getType4() == null : this.getType4().equals(thatObj.getType4())&&
            this.getType5() == null ? thatObj.getType5() == null : this.getType5().equals(thatObj.getType5())&&
            this.getType7() == null ? thatObj.getType7() == null : this.getType7().equals(thatObj.getType7())&&
            this.getType8() == null ? thatObj.getType8() == null : this.getType8().equals(thatObj.getType8())&&
            this.getType9() == null ? thatObj.getType9() == null : this.getType9().equals(thatObj.getType9())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getType1() ? 0 : getType1().hashCode());
        hash = 31 * hash + (null == getType10() ? 0 : getType10().hashCode());
        hash = 31 * hash + (null == getType12() ? 0 : getType12().hashCode());
        hash = 31 * hash + (null == getType13() ? 0 : getType13().hashCode());
        hash = 31 * hash + (null == getType16() ? 0 : getType16().hashCode());
        hash = 31 * hash + (null == getType17() ? 0 : getType17().hashCode());
        hash = 31 * hash + (null == getType19() ? 0 : getType19().hashCode());
        hash = 31 * hash + (null == getType2() ? 0 : getType2().hashCode());
        hash = 31 * hash + (null == getType20() ? 0 : getType20().hashCode());
        hash = 31 * hash + (null == getType3() ? 0 : getType3().hashCode());
        hash = 31 * hash + (null == getType4() ? 0 : getType4().hashCode());
        hash = 31 * hash + (null == getType5() ? 0 : getType5().hashCode());
        hash = 31 * hash + (null == getType7() ? 0 : getType7().hashCode());
        hash = 31 * hash + (null == getType8() ? 0 : getType8().hashCode());
        hash = 31 * hash + (null == getType9() ? 0 : getType9().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Test.type:";
        str +=  ("Identity = " + (null == type6 ? "null" : type6.toString())) + ", ";
        str +=  ("type1 = " + (null == getType1() ? "null" : getType1().toString())) + ", ";
        str +=  ("type10 = " + (null == getType10() ? "null" : getType10().toString())) + ", ";
        str +=  ("type12 = " + (null == getType12() ? "null" : getType12().toString())) + ", ";
        str +=  ("type13 = " + (null == getType13() ? "null" : getType13().toString())) + ", ";
        str +=  ("type16 = " + (null == getType16() ? "null" : getType16().toString())) + ", ";
        str +=  ("type17 = " + (null == getType17() ? "null" : getType17().toString())) + ", ";
        str +=  ("type19 = " + (null == getType19() ? "null" : getType19().toString())) + ", ";
        str +=  ("type2 = " + (null == getType2() ? "null" : getType2().toString())) + ", ";
        str +=  ("type20 = " + (null == getType20() ? "null" : getType20().toString())) + ", ";
        str +=  ("type3 = " + (null == getType3() ? "null" : getType3().toString())) + ", ";
        str +=  ("type4 = " + (null == getType4() ? "null" : getType4().toString())) + ", ";
        str +=  ("type5 = " + (null == getType5() ? "null" : getType5().toString())) + ", ";
        str +=  ("type7 = " + (null == getType7() ? "null" : getType7().toString())) + ", ";
        str +=  ("type8 = " + (null == getType8() ? "null" : getType8().toString())) + ", ";
        str +=  ("type9 = " + (null == getType9() ? "null" : getType9().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Type thatObj) {
    
        int cmp;

        cmp = this.getType1() == null ?
                (thatObj.getType1() == null ? 0 : -1) :
                (thatObj.getType1() == null ? 1 : this.getType1().compareTo(thatObj.getType1())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getType10() == null ?
                (thatObj.getType10() == null ? 0 : -1) :
                (thatObj.getType10() == null ? 1 : this.getType10().compareTo(thatObj.getType10())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getType12() == null ?
                (thatObj.getType12() == null ? 0 : -1) :
                (thatObj.getType12() == null ? 1 : this.getType12().compareTo(thatObj.getType12())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getType13() == null ?
                (thatObj.getType13() == null ? 0 : -1) :
                (thatObj.getType13() == null ? 1 : this.getType13().compareTo(thatObj.getType13())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getType16() == null ?
                (thatObj.getType16() == null ? 0 : -1) :
                (thatObj.getType16() == null ? 1 : this.getType16().compareTo(thatObj.getType16())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getType17() == null ?
                (thatObj.getType17() == null ? 0 : -1) :
                (thatObj.getType17() == null ? 1 : this.getType17().compareTo(thatObj.getType17())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getType19() == null ?
                (thatObj.getType19() == null ? 0 : -1) :
                (thatObj.getType19() == null ? 1 : this.getType19().compareTo(thatObj.getType19())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getType2() == null ?
                (thatObj.getType2() == null ? 0 : -1) :
                (thatObj.getType2() == null ? 1 : this.getType2().compareTo(thatObj.getType2())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getType20() == null ?
                (thatObj.getType20() == null ? 0 : -1) :
                (thatObj.getType20() == null ? 1 : this.getType20().compareTo(thatObj.getType20())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getType3() == null ?
                (thatObj.getType3() == null ? 0 : -1) :
                (thatObj.getType3() == null ? 1 : this.getType3().compareTo(thatObj.getType3())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getType4() == null ?
                (thatObj.getType4() == null ? 0 : -1) :
                (thatObj.getType4() == null ? 1 : this.getType4().compareTo(thatObj.getType4())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getType5() == null ?
                (thatObj.getType5() == null ? 0 : -1) :
                (thatObj.getType5() == null ? 1 : this.getType5().compareTo(thatObj.getType5())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getType7() == null ?
                (thatObj.getType7() == null ? 0 : -1) :
                (thatObj.getType7() == null ? 1 : this.getType7().compareTo(thatObj.getType7())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getType8() == null ?
                (thatObj.getType8() == null ? 0 : -1) :
                (thatObj.getType8() == null ? 1 : this.getType8().compareTo(thatObj.getType8())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getType9() == null ?
                (thatObj.getType9() == null ? 0 : -1) :
                (thatObj.getType9() == null ? 1 : this.getType9().compareTo(thatObj.getType9())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}