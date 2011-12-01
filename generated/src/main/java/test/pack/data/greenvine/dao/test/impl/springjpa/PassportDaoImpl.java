package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.PassportDao;
import test.pack.data.greenvine.entity.test.Passport;
import java.lang.String;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class PassportDaoImpl extends JpaDaoSupport implements PassportDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PassportDao#loadPassport(String)
     */
    @Override
    public Passport loadPassport(String passportNr) {
        return getJpaTemplate().getReference(Passport.class, passportNr);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PassportDao#findPassport(String)
     */
    @Override
    public Passport findPassport(String passportNr) {
        return getJpaTemplate().find(Passport.class, passportNr);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PassportDao#findAllPassports()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Passport> findAllPassports() {
        return getJpaTemplate().find("from test.Passport as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PassportDao#updatePassport(Passport)
     */
    @Override 
    public void updatePassport(Passport passport) {
        getJpaTemplate().merge(passport);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PassportDao#createPassport(Passport)
     */
    @Override
    public void createPassport(Passport passport) {
        getJpaTemplate().persist(passport);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PassportDao#removePassport(String)
     */
    @Override
    public void removePassport(String passportNr) {
         Passport passport = getJpaTemplate().getReference(Passport.class, passportNr);
         getJpaTemplate().remove(passport);

    }

}
