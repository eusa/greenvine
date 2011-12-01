package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.ActivityDao;
import test.pack.data.greenvine.entity.test.Activity;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class ActivityDaoImpl extends JpaDaoSupport implements ActivityDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ActivityDao#loadActivity(Integer)
     */
    @Override
    public Activity loadActivity(Integer activityId) {
        return getJpaTemplate().getReference(Activity.class, activityId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ActivityDao#findActivity(Integer)
     */
    @Override
    public Activity findActivity(Integer activityId) {
        return getJpaTemplate().find(Activity.class, activityId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ActivityDao#findAllActivitys()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Activity> findAllActivitys() {
        return getJpaTemplate().find("from test.Activity as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ActivityDao#updateActivity(Activity)
     */
    @Override 
    public void updateActivity(Activity activity) {
        getJpaTemplate().merge(activity);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ActivityDao#createActivity(Activity)
     */
    @Override
    public void createActivity(Activity activity) {
        getJpaTemplate().persist(activity);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ActivityDao#removeActivity(Integer)
     */
    @Override
    public void removeActivity(Integer activityId) {
         Activity activity = getJpaTemplate().getReference(Activity.class, activityId);
         getJpaTemplate().remove(activity);

    }

}
