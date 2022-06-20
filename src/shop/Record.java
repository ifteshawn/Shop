package shop;

/**
 *
 * @author Ifte
 */

/*
This class is to create a customer record as soon as he/she arrives.
It also maintains the customer state as he/she arrives and goes through the shopping process. 
It also uses an enum to model customer status;  
It uses a static counter to keep track of the number of customers that have arrived in the simulation and enables us 
to generate a unique customer number for each customer. 
 */
public class Record {

    public enum Status {
        NONE, TURNED_AWAY, SHOPPING, QUEUING, BEING_SERVED, FINISHED
    };
    private static int n = 0;       // to generate unique ids. E.g. with every record created, we do "n++" and then assign that value of n++ to custID
    private int customerNumber;
    private Status status;
    private int arrived;
    private int queued;
    private int served;
    private int departed;

    public Record() {
        n++;
        status = Status.NONE;
        customerNumber = n;
    }

    //This method is to reset the customer counter if the simulation gets reset.
    public static void reset() {
        n = 0;
    }

    // This method sets the status of the customer to the appropriate enum value passed into its update() method 
    // and based on the status sets the record’s appropriate time member variable(s) to maintain a record of when the customer arrived, departed, queued etc.
    public void update(Status s, int time) {
        setStatus(s);

        switch (s) {

            case TURNED_AWAY:

                setArrived(time);

                setDeparted(time);

                break;

            case SHOPPING:

                setArrived(time);

                break;

            case QUEUING:

                setQueued(time);

                break;

            case BEING_SERVED:

                setServed(time);

                break;

            case FINISHED:

                setDeparted(time);

                break;
        }

    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    //This method uses the static variabe "n" to generate unique custnum
    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getArrived() {
        return arrived;
    }

    public void setArrived(int arrived) {
        this.arrived = arrived;
    }

    public int getQueued() {
        return queued;
    }

    public void setQueued(int queued) {
        this.queued = queued;
    }

    public int getServed() {
        return served;
    }

    public void setServed(int served) {
        this.served = served;
    }

    public int getDeparted() {
        return departed;
    }

    public void setDeparted(int departed) {
        this.departed = departed;
    }

    @Override
    public String toString() {
        return String.format("C%d: <%s>", getCustomerNumber(), getStatus());
    }

}
