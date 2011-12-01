package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.ActivityDao;
import test.pack.data.greenvine.entity.test.Activity;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class ActivityDaoImpl extends HibernateDaoSupport implements ActivityDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ActivityDao#loadActivity(Integer)
     */
    @Override
    public Activity loadActivity(Integer activityId) {
        return (Activity) getHibernateTemplate().load(Activity.class, activityId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ActivityDao#findActivity(Integer)
     */
    @Override
    public Activity findActivity(Integer activityId) {
        return (Activity) getHibernateTemplate().get(Activity.class, activityId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ActivityDao#findAllActivitys()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Activity> findAllActivitys() {
        return (List<Activity>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.test.Activity as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ActivityDao#updateActivity(Activity)
     */
    @Override 
    public void updateActivity(Activity activity) {
        getHibernateTemplate().merge(activity);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ActivityDao#createActivity(Activity)
     */
    @Override
    public void createActivity(Activity activity) {
        getHibernateTemplate().persist(activity);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ActivityDao#removeActivity(Integer)
     */
    @Override
    public void removeActivity(Integer activityId) {
         Object activity = getHibernateTemplate().load(Activity.class, activityId);
         getHibernateTemplate().delete(activity);

    }

}
