package shop;

public class SimulationState {
    private String time;
    private String event;
    private String eventQueue;
    private ModelState modelState;
    
    public SimulationState() {
        time = "";
        event = "";
        eventQueue = "";
        modelState = null;
    }

    public String getTime() {
        return time;
    }

    public void setTime(int t) {
        time = Integer.toString(t);
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String s) {
        event = s;
    }

    public String getEventQueue() {
        return eventQueue;
    }

    public void setEventQueue(String s) {
        eventQueue = s;
    }
    
    public ModelState getModelState() {
        return modelState;
    }

    public void setModelState(ModelState ms) {
        modelState = ms;
    }
}
