package shop;

/**
 *
 * @author Ifte
 */
import static shop.Record.Status.*;   // In order to access customer status in the Model class, the enum values need to be imported.
import java.util.ArrayList;

// This class models the behaviour of the shop. The methods that realise shop and customer behavior are invoked from the process() methods of the appropriate events.
public class Model {

    private int serversAvailable;
    private final int allowedMaximum = 5;  //This member is final because there is always 5 maximum customer allowed within the shop.
    ArrayList<Record> queue;
    private final ModelState modelState;

    public Model() {
        Record.reset();
        queue = new ArrayList<>();
        modelState = new ModelState();
        serversAvailable = 2;
    }

    public ModelState getModelState() {
        return modelState;
    }

    /*However, in this phase, there is no limit, so turnAway() must  always return false in this phase. 
    In addition, turnAway() must add the record that is passed in as a parameter to the log and display- 
    -a trace statement on the console to show the customer arrival. 
    turnAway() will be called by the ArrivalEvent class’s process() method; 
    In the ArrvialEvent’s process() method,  if turnAway() returns false, then it means the customer- 
    -can enter the shop and so the Model class’s enter() method must be called. */
    public boolean turnAway(int time, Record record) {
        modelState.addRecord(record);
        System.out.println("t= " + time + ": " + "Customer " + record.getCustomerNumber() + " arrives.");
        if (modelState.getNumberInShop() >= allowedMaximum) {
            record.update(TURNED_AWAY, time);
            modelState.updateCounts(record);
            System.out.println("t= " + time + ": " + "Customer " + record.getCustomerNumber() + " turns away.");
            return true;
        } else {
            return false;
        }
    }

    // This method is to update the record passed in as its parameter to indicate that the customer is in the SHOPPING state and to record the time at which this SHOPPING activity started.
    // It then displays a trace statement on the console saying the customer is shopping.
    // It then calls the ModelState class’s updateCounts() method to increment the number of customers inside the shop.
    public void enter(int time, Record record) {
        record.update(SHOPPING, time);
        System.out.println("t= " + time + ": " + "Customer " + record.getCustomerNumber() + " begins shopping.");
        modelState.updateCounts(record);
    }

    // This method adds the customer to the end of the checkout queue in the Model class.
    // It then calls the Record class’s update() method to update the customer record to have a status of QUEUING. 
    // It also prints out a trace statement showing that the customer has joined the queue and stating the queue length.
    public void joinQueue(int time, Record record) {
        queue.add(record);
        record.update(QUEUING, time);
        System.out.println("t= " + time + ": " + "Customer " + record.getCustomerNumber() + " joins checkout queue. Queue length is now " + queue.size());
    }

    // This is to test if the customer is at the head/front of the checkout queue and if a server is available.
    // If both are true then it returns that the customer cna be served.
    public boolean canBeServed(Record record) {
        if (isHeadOfQueue(record) == true && getServersAvailable() > 0) {
            return true;
        }
        return false;
    }

    // This method decrements the number of servers available.
    // Checks if it is the right customer we are to serve.
    // It then removes the customer from the head of the queue.
    // Then updates the customer record to the BEING_SERVED status.      
    // Prints out the simulation trace statement showing the customer being served.
    public void serve(int time, Record record) {
        decrementServersAvailable();
        for (Record r : queue) {
            if (r == record) {
                queue.remove(r);
                record.update(BEING_SERVED, time);
            }
            break;
        }
        System.out.println("t= " + time + ": " + "Customer " + record.getCustomerNumber() + " is being served. Queue length is now " + queue.size() + ".");

    }

    // The Model class’s leave() method updates the customer record (using the customer record’s update method to set its status to FINISHED)
    // It then calls the model state’s  updateCounts() method and incremements the number of servers available to ensure that the model and model state are updated correctly.
    // It then prints out an appropriate trace statement to show the customer leaving.
    public void leave(int time, Record record) {
        record.update(FINISHED, time);
        modelState.updateCounts(record);
        incrementServersAvailable();
        System.out.println("t= " + time + ": " + "Customer " + record.getCustomerNumber() + " leaves. There is/are now " + getServersAvailable() + " server(s) free.");
    }

    // This method is to get the next customer on queue.
    public Record nextOnQueue() {
        return queue.get(0);
    }

    //  isHeadOfQueue()  - tests if the customer is at the head/front of the checkout queue    
    public boolean isHeadOfQueue(Record r) {
        if (queue.get(0) == r) {
            return true;
        }
        return false;
    }

    /**
     * @return the serversAvailable
     */
    public int getServersAvailable() {
        return serversAvailable;
    }

    /**
     * @param serversAvailable the serversAvailable to set
     */
    public void setServersAvailable(int serversAvailable) {
        this.serversAvailable = serversAvailable;
    }

    public void decrementServersAvailable() {
        serversAvailable--;
    }

    public void incrementServersAvailable() {
        serversAvailable++;
    }

}
