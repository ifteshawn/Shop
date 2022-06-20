package shop;

/**
 *
 * @author Ifte
 */
// This class is to create a checkout event for when a customer is shopping.
public class CheckOutEvent extends Event {

    public CheckOutEvent(int t, Record r) {
        super(t, r);
    }

    // This method makes the customer join the checkout queue after shopping.
    // It then schedules a new service event immediately for the customer if able to serve and also prints a trace statement saying the customer could be served.
    @Override
    public void process(Model m, ISchedule s) {
        m.joinQueue(time, record);
        if (m.canBeServed(record) == true) {
            s.schedule(new ServiceEvent(time, record));
            System.out.printf("T= %d: CheckOutEvent (C%d) could be served\n", time, record.getCustomerNumber());
        }
    }

    @Override
    public String toString() {
        return String.format("T = %4d: CheckOutEvent (C%d)", time, record.getCustomerNumber());
    }

}
