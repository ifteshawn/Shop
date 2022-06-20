package shop;

/**
 *
 * @author Ifte
 */
// This class is to create a service event for every customer that is at the head of the checkout queue.
public class ServiceEvent extends Event {

    public ServiceEvent(int t, Record r) {
        super(t, r);
    }

//  This method calls the Model class’s serve() method to serve the customer. 
//  Then it schedules the leave event for the customer after 5 time units.
    @Override
    public void process(Model m, ISchedule s) {
        m.serve(time, record);
        s.schedule(new LeaveEvent(time + 5, record));
    }

    @Override
    public String toString() {
        return String.format("T = %4d: ServiceEvent (C%d)", time, record.getCustomerNumber());
    }

}
