package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.ProfileDao;
import test.pack.data.greenvine.entity.test.Profile;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class ProfileDaoImpl extends HibernateDaoSupport implements ProfileDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ProfileDao#loadProfile(Integer)
     */
    @Override
    public Profile loadProfile(Integer profileId) {
        return (Profile) getHibernateTemplate().load(Profile.class, profileId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ProfileDao#findProfile(Integer)
     */
    @Override
    public Profile findProfile(Integer profileId) {
        return (Profile) getHibernateTemplate().get(Profile.class, profileId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ProfileDao#findAllProfiles()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Profile> findAllProfiles() {
        return (List<Profile>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.test.Profile as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ProfileDao#updateProfile(Profile)
     */
    @Override 
    public void updateProfile(Profile profile) {
        getHibernateTemplate().merge(profile);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ProfileDao#createProfile(Profile)
     */
    @Override
    public void createProfile(Profile profile) {
        getHibernateTemplate().persist(profile);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ProfileDao#removeProfile(Integer)
     */
    @Override
    public void removeProfile(Integer profileId) {
         Object profile = getHibernateTemplate().load(Profile.class, profileId);
         getHibernateTemplate().delete(profile);

    }

}
