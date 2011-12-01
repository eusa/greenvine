package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;
import java.lang.Integer;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "test.Profile")
@Table(name = "TEST.TBL_PROFILE", uniqueConstraints = { @UniqueConstraint(columnNames = { "FK_USERNAME" }) })  
public class Profile implements Comparable<Profile>, Serializable {

    private static final long serialVersionUID = 7998759480869901528L;

    /**
    * Identity field
    */
    private Integer profileId;

    /**
    * NaturalIdentity field
    */
    private User user;

    /**
    * screenName field
    */
    private String screenName;
    
    /**
    * friendRequestees field
    */
    private Collection<Profile> friendRequestees = new TreeSet<Profile>();

    /**
    * friendRequesters field
    */
    private Collection<Profile> friendRequesters = new TreeSet<Profile>();

    /**
    * spouseFrom field
    */
    private Profile spouseFrom;

    /**
    * spouseTo field
    */
    private Profile spouseTo;

    /**
    * Default constructor
    */
    public Profile() {
    }
    
    /**
    * Simple Property constructor
    */
    public Profile(Integer profileId, User user, String screenName) {
        this.profileId = profileId;
        this.user = user;
        this.screenName = screenName;
    }

    /**
    * Full Property constructor
    */
    public Profile(Integer profileId, User user, String screenName, Collection<Profile> friendRequestees, Collection<Profile> friendRequesters, Profile spouseFrom, Profile spouseTo) {
        this.profileId = profileId;
        this.user = user;
        this.screenName = screenName;
        this.friendRequestees = friendRequestees;
        this.friendRequesters = friendRequesters;
        this.spouseFrom = spouseFrom;
        this.spouseTo = spouseTo;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "PROFILE_ID", nullable = false)
    public Integer getProfileId() {
        return this.profileId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    /**
    * Accessor for the natural identity field user
    * @returns the value of the field user
    */
            // @NaturalId 
    /**
    * Accessor for user field
    * @return the value of the user field. 
    */
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.test.User.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "FK_USERNAME", referencedColumnName = "USERNAME",  nullable = false)    
    } )
    public User getUser() {
        return this.user;
    }
    
    /**
    * Mutator for the natural identity field user
    * @param sets the value of the user field
    */
    public void setUser(User user) {
        this.user = user;
    }

    /**
    * Accessor for screenName field
    * returns the value of the screenName field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "SCREEN_NAME",  nullable = false, columnDefinition = "VARCHAR(100)")
    public String getScreenName() {
        return this.screenName;
    }
          
    /**
    * Mutator for the screenName field
    * @param  sets the value of the screenName field
    */    
    public void setScreenName(String screenName) {
      this.screenName = screenName;
    }
          

    /**
    * Accessor for friendRequestees field
    * @return the value of the friendRequestees field. 
    */
    @ManyToMany(targetEntity = test.pack.data.greenvine.entity.test.Profile.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
        name = "TEST.TBL_FRIEND",
        joinColumns = { @JoinColumn(name = "REQUESTER_ID", referencedColumnName = "PROFILE_ID", nullable = false) },
        inverseJoinColumns = { @JoinColumn(name = "REQUESTEE_ID", referencedColumnName = "PROFILE_ID", nullable = false) }
    )
    public Collection<Profile> getFriendRequestees() {
        return this.friendRequestees;
    }
      
      /**
      * Mutator for friendRequestees field
      * @param friendRequestees the new value for the friendRequestees field
      */        
      public void setFriendRequestees(Collection<Profile> friendRequestees) {
        this.friendRequestees = friendRequestees;
      }
          

    /**
    * Accessor for friendRequesters field
    * @return the value of the friendRequesters field. 
    */
    @ManyToMany(targetEntity = test.pack.data.greenvine.entity.test.Profile.class, mappedBy = "friendRequestees", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Collection<Profile> getFriendRequesters() {
        return this.friendRequesters;
    }
      
      /**
      * Mutator for friendRequesters field
      * @param friendRequesters the new value for the friendRequesters field
      */        
      public void setFriendRequesters(Collection<Profile> friendRequesters) {
        this.friendRequesters = friendRequesters;
      }
          

    /**
    * Accessor for spouseFrom field
    * @return the value of the spouseFrom field. 
    */
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.test.Profile.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
        name = "TEST.TBL_SPOUSE",
        joinColumns = { @JoinColumn(name = "SPOUSE_TO_ID", referencedColumnName = "PROFILE_ID", nullable = false) },
        inverseJoinColumns = { @JoinColumn(name = "SPOUSE_FROM_ID", referencedColumnName = "PROFILE_ID", nullable = false) }, 
        uniqueConstraints = { @UniqueConstraint(columnNames = { "SPOUSE_TO_ID" }), @UniqueConstraint(columnNames = { "SPOUSE_FROM_ID" }) }
    )
    public Profile getSpouseFrom() {
        return this.spouseFrom;
    }
      
      /**
      * Mutator for spouseFrom field
      * @param spouseFrom the new value for the spouseFrom field
      */        
      public void setSpouseFrom(Profile spouseFrom) {
        this.spouseFrom = spouseFrom;
      }
          

    /**
    * Accessor for spouseTo field
    * @return the value of the spouseTo field. 
    */
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.test.Profile.class, mappedBy = "spouseFrom", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Profile getSpouseTo() {
        return this.spouseTo;
    }
      
      /**
      * Mutator for spouseTo field
      * @param spouseTo the new value for the spouseTo field
      */        
      public void setSpouseTo(Profile spouseTo) {
        this.spouseTo = spouseTo;
      }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Test.profile
        if ( !(that instanceof Profile) ) return false;

        // Safely cast to Test.profile
        Profile thatObj = (Profile)that;

        // Equality is based on natural id
        return
            this.getUser() == null ? thatObj.getUser() == null : this.getUser().equals(thatObj.getUser()) && 
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on natural id
        hash = 31 * hash + (null == getUser() ? 0 : getUser().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Test.profile:";
        str +=  ("Identity = " + (null == profileId ? "null" : profileId.toString())) + ", ";
        str +=  ("screenName = " + (null == getScreenName() ? "null" : getScreenName().toString())) + ", ";
        str +=  ("user = " + (null == getUser() ? "null" : getUser().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Profile thatObj) {
    
        int cmp;

        cmp = this.getUser().compareTo(thatObj.getUser());
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}