package shop;

public abstract class Event {
    
    // In this case, protected is effectively public, because all classes are in 
    // a single package and they can all access time and record. What we should really 
    // do is to put the event classes into their own package. However, we are
    // only having one package (shop) in this assignment
    protected int time;
    protected Record record;
    
    public abstract void process( Model sm, ISchedule s );

    public Event( int t, Record r ) {
        time = t;
        record = r;
    }
    
    public int getTime() {
        return time;
    }
      
    @Override
    public String toString() {
        return "Event";
    }

}
