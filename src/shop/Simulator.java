package shop;

import java.util.ArrayList;
import java.util.Scanner;

public class Simulator implements ISchedule{
    private ArrayList<Event> events; 
    private int clock;
    private Model model;
    private IUpdate updater;
    private SimulationState simulationState;
    
    public Simulator( Model m, Event e, IUpdate u ) { 
        clock = 0;
        model = m;
        events = new ArrayList<>();
        events.add( e );
        updater = u;
        simulationState = new SimulationState();
    }
    
    public SimulationState run( int until, boolean stepMode ) throws IllegalStateException {
        if (events == null || events.isEmpty()) {
            throw new IllegalStateException("Event queue is empty or null");
        }
        // No event has been processed yet, but there is an event on the queue.
        
        // Display the starting state in the GUI
        setSimulationState("");
        updater.update(simulationState);
        // get first event
        Event e = events.remove(0);
        clock = e.getTime();
        while (clock <= until && stepping(stepMode)) {
            e.process(model, this);
            setSimulationState( e.toString() );
            updater.update(simulationState);
            // get next event
            e = events.remove(0);
            clock = e.getTime();
        }
        return simulationState;
    }
    
    // simulation state = simulator state + model state
    private void setSimulationState( String e ) {
        // set simulator state for display in the GUI
        simulationState.setTime(clock);
        simulationState.setEvent(e);
        simulationState.setEventQueue(events());
        
        // set model state for display in the GUI
        simulationState.setModelState(model.getModelState());
    }
    
    private boolean stepping(boolean mode) {
        if (mode) {
            String choice = updater.choose( "Next Action?", "Step", "Stop");
            if ( choice.equals( "Stop" ) )
                return false;  
        }
        return true;
    }
    
    //This method inserts events into the event queue in time order.
    @Override
    public void schedule( Event e ) {
        for (int i = 0; i < events.size(); i++){
            if (events.get(i).getTime() > e.getTime()) {
                events.add(i, e);
                return;
            }
        }
        events.add(e);
    }
           
       
    public String events() {
        StringBuilder sb = new StringBuilder();
        for (Event e : events)
            sb.append(String.format("%s\n", e));
        return sb.toString();
    }   
}
