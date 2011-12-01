package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.TimesheetDao;
import test.pack.data.greenvine.entity.test.Timesheet;
import test.pack.data.greenvine.entity.test.TimesheetIdentity;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class TimesheetDaoImpl extends HibernateDaoSupport implements TimesheetDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TimesheetDao#loadTimesheet(TimesheetIdentity)
     */
    @Override
    public Timesheet loadTimesheet(TimesheetIdentity timesheetIdentity) {
        return (Timesheet) getHibernateTemplate().load(Timesheet.class, timesheetIdentity);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TimesheetDao#findTimesheet(TimesheetIdentity)
     */
    @Override
    public Timesheet findTimesheet(TimesheetIdentity timesheetIdentity) {
        return (Timesheet) getHibernateTemplate().get(Timesheet.class, timesheetIdentity);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TimesheetDao#findAllTimesheets()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Timesheet> findAllTimesheets() {
        return (List<Timesheet>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.test.Timesheet as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TimesheetDao#updateTimesheet(Timesheet)
     */
    @Override 
    public void updateTimesheet(Timesheet timesheet) {
        getHibernateTemplate().merge(timesheet);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TimesheetDao#createTimesheet(Timesheet)
     */
    @Override
    public void createTimesheet(Timesheet timesheet) {
        getHibernateTemplate().persist(timesheet);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TimesheetDao#removeTimesheet(TimesheetIdentity)
     */
    @Override
    public void removeTimesheet(TimesheetIdentity timesheetIdentity) {
         Object timesheet = getHibernateTemplate().load(Timesheet.class, timesheetIdentity);
         getHibernateTemplate().delete(timesheet);

    }

}
