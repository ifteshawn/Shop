package shop;

/**
 *
 * @author Ifte
 */
// This class is to create a leave event for when a customer is being served.
public class LeaveEvent extends Event {

    public LeaveEvent(int t, Record r) {
        super(t, r);
    }

    // This method makes a customer leave the shop after being served by calling the model's leave method. 
    // It then schedules a new service event immediately to get the next customer in queue to be served.
    @Override
    public void process(Model m, ISchedule s) {
        m.leave(time, record);
        s.schedule(new ServiceEvent(time, m.nextOnQueue()));
    }

    @Override
    public String toString() {
        return String.format("T = %4d: LeaveEvent (C%d)", time, record.getCustomerNumber());
    }

}
