package shop;

public interface IUpdate {
    public void update(SimulationState s );
    public void warning(String s );
    public String choose( String q, String a1, String a2 );
}
