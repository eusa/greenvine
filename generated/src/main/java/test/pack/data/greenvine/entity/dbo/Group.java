package test.pack.data.greenvine.entity.dbo;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;
import java.lang.Integer;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "dbo.Group")
@Table(name = "DBO.TBL_GROUP", uniqueConstraints = { @UniqueConstraint(columnNames = { "GROUPNAME" }) })  
public class Group implements Comparable<Group>, Serializable {

    private static final long serialVersionUID = -7396796291012225261L;

    /**
    * Identity field
    */
    private Integer groupId;

    /**
    * NaturalIdentity field
    */
    private String groupname;

    /**
    * users field
    */
    private Collection<User> users = new TreeSet<User>();

    /**
    * Default constructor
    */
    public Group() {
    }
    
    /**
    * Simple Property constructor
    */
    public Group(Integer groupId, String groupname) {
        this.groupId = groupId;
        this.groupname = groupname;
    }

    /**
    * Full Property constructor
    */
    public Group(Integer groupId, String groupname, Collection<User> users) {
        this.groupId = groupId;
        this.groupname = groupname;
        this.users = users;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "GROUP_ID", nullable = false)
    public Integer getGroupId() {
        return this.groupId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
    * Accessor for the natural identity field groupname
    * @returns the value of the field groupname
    */
            // @NaturalId 
    public String getGroupname() {
        return this.groupname;
    }
    
    /**
    * Mutator for the natural identity field groupname
    * @param sets the value of the groupname field
    */
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }


    /**
    * Accessor for users field
    * @return the value of the users field. 
    */
    @ManyToMany(targetEntity = test.pack.data.greenvine.entity.dbo.User.class, mappedBy = "groups", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Collection<User> getUsers() {
        return this.users;
    }
      
      /**
      * Mutator for users field
      * @param users the new value for the users field
      */        
      public void setUsers(Collection<User> users) {
        this.users = users;
      }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Dbo.group
        if ( !(that instanceof Group) ) return false;

        // Safely cast to Dbo.group
        Group thatObj = (Group)that;

        // Equality is based on natural id
        return
            this.getGroupname() == null ? thatObj.getGroupname() == null : this.getGroupname().equals(thatObj.getGroupname()) && 
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on natural id
        hash = 31 * hash + (null == getGroupname() ? 0 : getGroupname().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Dbo.group:";
        str +=  ("Identity = " + (null == groupId ? "null" : groupId.toString())) + ", ";
        str +=  ("groupname = " + (null == getGroupname() ? "null" : getGroupname().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Group thatObj) {
    
        int cmp;

        cmp = this.getGroupname().compareTo(thatObj.getGroupname());
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}