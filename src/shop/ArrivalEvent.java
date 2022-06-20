package shop;

public class ArrivalEvent extends Event {

    // I could have had an ArrivalEvent(int t) and created the (empty) record for 
    // this customer in the constructor. I didn't do it in order to retain consistency 
    // with the other events.
    public ArrivalEvent(int t, Record r) {
        super(t, r);
    }

    // This ArrivalEvent process method enters customer record into the event queue and schedules a new checkout event after 1 time unit if the customer is not turned away.
    // It also schedules a new arrival event for a new customer after 1 time unit
    @Override
    public void process(Model m, ISchedule s) {
        if (m.turnAway(time, record) == false) {
            m.enter(time, record);
            s.schedule(new CheckOutEvent(time + 1, record));
        }
        s.schedule(new ArrivalEvent(time + 1, new Record()));

    }

    @Override
    public String toString() {
        return String.format("T = %4d: ArrivalEvent (C%d)", time, record.getCustomerNumber());
    }

}
