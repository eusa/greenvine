package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.PassportDao;
import test.pack.data.greenvine.entity.test.Passport;
import java.lang.String;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class PassportDaoImpl extends HibernateDaoSupport implements PassportDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PassportDao#loadPassport(String)
     */
    @Override
    public Passport loadPassport(String passportNr) {
        return (Passport) getHibernateTemplate().load(Passport.class, passportNr);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PassportDao#findPassport(String)
     */
    @Override
    public Passport findPassport(String passportNr) {
        return (Passport) getHibernateTemplate().get(Passport.class, passportNr);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PassportDao#findAllPassports()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Passport> findAllPassports() {
        return (List<Passport>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.test.Passport as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PassportDao#updatePassport(Passport)
     */
    @Override 
    public void updatePassport(Passport passport) {
        getHibernateTemplate().merge(passport);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PassportDao#createPassport(Passport)
     */
    @Override
    public void createPassport(Passport passport) {
        getHibernateTemplate().persist(passport);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PassportDao#removePassport(String)
     */
    @Override
    public void removePassport(String passportNr) {
         Object passport = getHibernateTemplate().load(Passport.class, passportNr);
         getHibernateTemplate().delete(passport);

    }

}
