package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.ProfileDao;
import test.pack.data.greenvine.entity.test.Profile;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class ProfileDaoImpl extends JpaDaoSupport implements ProfileDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ProfileDao#loadProfile(Integer)
     */
    @Override
    public Profile loadProfile(Integer profileId) {
        return getJpaTemplate().getReference(Profile.class, profileId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ProfileDao#findProfile(Integer)
     */
    @Override
    public Profile findProfile(Integer profileId) {
        return getJpaTemplate().find(Profile.class, profileId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ProfileDao#findAllProfiles()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Profile> findAllProfiles() {
        return getJpaTemplate().find("from test.Profile as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ProfileDao#updateProfile(Profile)
     */
    @Override 
    public void updateProfile(Profile profile) {
        getJpaTemplate().merge(profile);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ProfileDao#createProfile(Profile)
     */
    @Override
    public void createProfile(Profile profile) {
        getJpaTemplate().persist(profile);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ProfileDao#removeProfile(Integer)
     */
    @Override
    public void removeProfile(Integer profileId) {
         Profile profile = getJpaTemplate().getReference(Profile.class, profileId);
         getJpaTemplate().remove(profile);

    }

}
