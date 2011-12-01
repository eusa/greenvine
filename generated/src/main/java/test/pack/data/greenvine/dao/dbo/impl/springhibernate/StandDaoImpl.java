package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.dbo.StandDao;
import test.pack.data.greenvine.entity.dbo.Stand;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class StandDaoImpl extends HibernateDaoSupport implements StandDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.StandDao#loadStand(Integer)
     */
    @Override
    public Stand loadStand(Integer standId) {
        return (Stand) getHibernateTemplate().load(Stand.class, standId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.StandDao#findStand(Integer)
     */
    @Override
    public Stand findStand(Integer standId) {
        return (Stand) getHibernateTemplate().get(Stand.class, standId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.StandDao#findAllStands()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Stand> findAllStands() {
        return (List<Stand>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.dbo.Stand as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.StandDao#updateStand(Stand)
     */
    @Override 
    public void updateStand(Stand stand) {
        getHibernateTemplate().merge(stand);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.StandDao#createStand(Stand)
     */
    @Override
    public void createStand(Stand stand) {
        getHibernateTemplate().persist(stand);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.StandDao#removeStand(Integer)
     */
    @Override
    public void removeStand(Integer standId) {
         Object stand = getHibernateTemplate().load(Stand.class, standId);
         getHibernateTemplate().delete(stand);

    }

}
