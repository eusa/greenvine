package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.dbo.UmbrellaDao;
import test.pack.data.greenvine.entity.dbo.Umbrella;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class UmbrellaDaoImpl extends HibernateDaoSupport implements UmbrellaDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UmbrellaDao#loadUmbrella(Integer)
     */
    @Override
    public Umbrella loadUmbrella(Integer umbrellaId) {
        return (Umbrella) getHibernateTemplate().load(Umbrella.class, umbrellaId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UmbrellaDao#findUmbrella(Integer)
     */
    @Override
    public Umbrella findUmbrella(Integer umbrellaId) {
        return (Umbrella) getHibernateTemplate().get(Umbrella.class, umbrellaId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UmbrellaDao#findAllUmbrellas()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Umbrella> findAllUmbrellas() {
        return (List<Umbrella>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.dbo.Umbrella as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UmbrellaDao#updateUmbrella(Umbrella)
     */
    @Override 
    public void updateUmbrella(Umbrella umbrella) {
        getHibernateTemplate().merge(umbrella);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UmbrellaDao#createUmbrella(Umbrella)
     */
    @Override
    public void createUmbrella(Umbrella umbrella) {
        getHibernateTemplate().persist(umbrella);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UmbrellaDao#removeUmbrella(Integer)
     */
    @Override
    public void removeUmbrella(Integer umbrellaId) {
         Object umbrella = getHibernateTemplate().load(Umbrella.class, umbrellaId);
         getHibernateTemplate().delete(umbrella);

    }

}
