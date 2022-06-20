package shop;

/**
 *
 * @author Ifte
 */

// This interface is add the functionality of scheduling events in the event queue according to their scheduled time. 
// This interface's schedule method is going to be called as part of an event’s process() method to schedule other events.
// This functional interface allows us to use different simulator or scheduler without needing to make changes accross other event classes process() method.
public interface ISchedule {
    
    public void schedule( Event e );
       
}
