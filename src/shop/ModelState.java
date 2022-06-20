package shop;

/**
 *
 * @author Ifte
 */
import java.util.ArrayList;
import shop.Record.Status;

// This class maintains the state of the shop – this is recorded in the log (ArrayLIst). 
public class ModelState {

    //members declared here has scope all over the class
    private ArrayList<Record> log;  //Log contains the records for ALL customers in the simulation.
    private int numberTurnedAway;   //to track the number of customers turned away.
    private int numberInShop;       //to track the number of customers currently in the shop.

    // if members decared within the constructor method then scope would only be within the constructor.
    public ModelState() {
        log = new ArrayList<>();
    }

    // addRecord() – adds the customer record to the log
    public void addRecord(Record r) {
        log.add(r);
    }

    // If the customer record status is SHOPPING then this increments the number in the shop. 
    // If it is FINISHED , it decrements the number in the shop as this means the customer has finished and left the shop. 
    // If the customer was turned away on arrival and the state is TURNED_AWAY, then the turned away count is incremented. 
    public void updateCounts(Record r) {
        Status s = r.getStatus();
        switch (s) {
            case SHOPPING:
                numberInShop++;
                break;
            case FINISHED:
                numberInShop--;
                break;
            case TURNED_AWAY:
                numberTurnedAway++;
                break;
            default:
                break;
        }
    }

    // This is to get the customers who are shopping and displaying in the shopping area of GUI. 
    public String getPeopleShopping() {
        StringBuffer s = new StringBuffer();
        for (Record r : log) {
            if (r.getStatus() == Status.SHOPPING) {
                s.append(String.format("C%d: shopping since t = %d\n", r.getCustomerNumber(), r.getArrived()));
            }
        }
        return s.toString();
    }

    //  This method returns the String with the list of people in the checkout queue.  
    //  The list of customers in the queue will be displayed in the queue text area in the GUI.
    public String getPeopleQueuing() {
        StringBuilder sb1 = new StringBuilder();
        for (Record r : log) {
            if (r.getStatus() == Status.QUEUING) {
                sb1.append(String.format("C%d: queued since t = %d\n", r.getCustomerNumber(), r.getQueued()));
            }
        }
        return sb1.toString();
    }

    //  This method returns the String with the list of people in the being served.  
    //  The list of customers being served will be displayed in the service text area in the GUI.
    public String getPeopleBeingServed() {
        StringBuilder sb2 = new StringBuilder();
        for (Record r : log) {
            if (r.getStatus() == Status.BEING_SERVED) {
                sb2.append(String.format("C%d: being served since t = %d\n", r.getCustomerNumber(), r.getServed()));
            }
        }
        return sb2.toString();
    }

    // getLog() – to return a String with the information about all the customers in the log and displaying in log area of the GUI.
    public String getLog() {
        StringBuilder sb = new StringBuilder();
        for (Record r : log) {
            sb.append(String.format("C%d: <%s, %d, %d, %d, %d>\n", r.getCustomerNumber(), r.getStatus(), r.getArrived(), r.getQueued(), r.getServed(), r.getDeparted()));
        }
        return sb.toString();
    }

    // getNumberInShop() – return the number of customers in the shop.
    public int getNumberInShop() {
        return numberInShop;
    }

    // To get the number of people turned away.
    public int getNumberTurnedAway() {
        return numberTurnedAway;
    }
}
