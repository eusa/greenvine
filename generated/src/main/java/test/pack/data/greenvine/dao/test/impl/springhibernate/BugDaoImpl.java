package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.BugDao;
import test.pack.data.greenvine.entity.test.Bug;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class BugDaoImpl extends HibernateDaoSupport implements BugDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.BugDao#loadBug(Integer)
     */
    @Override
    public Bug loadBug(Integer bugId) {
        return (Bug) getHibernateTemplate().load(Bug.class, bugId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.BugDao#findBug(Integer)
     */
    @Override
    public Bug findBug(Integer bugId) {
        return (Bug) getHibernateTemplate().get(Bug.class, bugId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.BugDao#findAllBugs()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Bug> findAllBugs() {
        return (List<Bug>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.test.Bug as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.BugDao#updateBug(Bug)
     */
    @Override 
    public void updateBug(Bug bug) {
        getHibernateTemplate().merge(bug);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.BugDao#createBug(Bug)
     */
    @Override
    public void createBug(Bug bug) {
        getHibernateTemplate().persist(bug);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.BugDao#removeBug(Integer)
     */
    @Override
    public void removeBug(Integer bugId) {
         Object bug = getHibernateTemplate().load(Bug.class, bugId);
         getHibernateTemplate().delete(bug);

    }

}
