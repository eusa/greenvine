package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.BugDao;
import test.pack.data.greenvine.entity.test.Bug;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class BugDaoImpl extends JpaDaoSupport implements BugDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.BugDao#loadBug(Integer)
     */
    @Override
    public Bug loadBug(Integer bugId) {
        return getJpaTemplate().getReference(Bug.class, bugId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.BugDao#findBug(Integer)
     */
    @Override
    public Bug findBug(Integer bugId) {
        return getJpaTemplate().find(Bug.class, bugId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.BugDao#findAllBugs()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Bug> findAllBugs() {
        return getJpaTemplate().find("from test.Bug as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.BugDao#updateBug(Bug)
     */
    @Override 
    public void updateBug(Bug bug) {
        getJpaTemplate().merge(bug);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.BugDao#createBug(Bug)
     */
    @Override
    public void createBug(Bug bug) {
        getJpaTemplate().persist(bug);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.BugDao#removeBug(Integer)
     */
    @Override
    public void removeBug(Integer bugId) {
         Bug bug = getJpaTemplate().getReference(Bug.class, bugId);
         getJpaTemplate().remove(bug);

    }

}
