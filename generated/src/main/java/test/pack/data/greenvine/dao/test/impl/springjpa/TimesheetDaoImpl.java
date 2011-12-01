package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.TimesheetDao;
import test.pack.data.greenvine.entity.test.Timesheet;
import test.pack.data.greenvine.entity.test.TimesheetIdentity;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class TimesheetDaoImpl extends JpaDaoSupport implements TimesheetDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TimesheetDao#loadTimesheet(TimesheetIdentity)
     */
    @Override
    public Timesheet loadTimesheet(TimesheetIdentity timesheetIdentity) {
        return getJpaTemplate().getReference(Timesheet.class, timesheetIdentity);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TimesheetDao#findTimesheet(TimesheetIdentity)
     */
    @Override
    public Timesheet findTimesheet(TimesheetIdentity timesheetIdentity) {
        return getJpaTemplate().find(Timesheet.class, timesheetIdentity);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TimesheetDao#findAllTimesheets()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Timesheet> findAllTimesheets() {
        return getJpaTemplate().find("from test.Timesheet as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TimesheetDao#updateTimesheet(Timesheet)
     */
    @Override 
    public void updateTimesheet(Timesheet timesheet) {
        getJpaTemplate().merge(timesheet);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TimesheetDao#createTimesheet(Timesheet)
     */
    @Override
    public void createTimesheet(Timesheet timesheet) {
        getJpaTemplate().persist(timesheet);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TimesheetDao#removeTimesheet(TimesheetIdentity)
     */
    @Override
    public void removeTimesheet(TimesheetIdentity timesheetIdentity) {
         Timesheet timesheet = getJpaTemplate().getReference(Timesheet.class, timesheetIdentity);
         getJpaTemplate().remove(timesheet);

    }

}
