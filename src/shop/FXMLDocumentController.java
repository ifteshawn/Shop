package shop;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.beans.value.*;

public class FXMLDocumentController implements Initializable, IUpdate {
    
    private Simulator simulator;
    int x = 0;
    
    @FXML
    private RadioButton stepMode;

    @FXML
    private Button simulate;
    
    @FXML
    private Button exit;

    @FXML
    private Button reset;

    @FXML
    private TextField duration;

    @FXML
    private TextField lastEvent;

    @FXML
    private TextArea shoppingArea;

    @FXML
    private TextArea checkoutQueue;

    @FXML
    private TextArea eventQueue;

    @FXML
    private TextArea log;
    
    @FXML
    private TextArea serviceArea;

    @FXML
    private TextField time;

    @FXML
    private TextField entered;
        
    @FXML
    private TextField notEntered;

    @FXML
    void simulateAction(ActionEvent event) {
        reset.setDisable( true );
        boolean step = false;
        if ( stepMode.isSelected() )
            step = true;
        String d = duration.getText();
        if (d.equals("")) {
            warning( "Enter duration"  );
            return;
        }
        int t = Integer.parseInt(d);
        try {
            simulator.run( t, step );
        }
        catch ( IllegalStateException ise ) {
            ise.printStackTrace();
            System.exit( 1 );
        }
        simulate.setDisable( true );
        reset.setDisable( false );
    }

    @FXML
    void exitAction(ActionEvent event) {
        System.exit( 0 );
    }
    
    @FXML
    void resetAction(ActionEvent event) {
        clear();
        reset.setDisable( true );
        simulate.setDisable( false );
        simulator = new Simulator( new Model(), new ArrivalEvent(1, new Record()), this );
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reset.setDisable( true );
        simulate.setDisable( false );
        // In keeping with best practice, I would rather have created the simulator 
        // and the model in main(), but the JavaFX launching model makes this 
        // more complex than can be justified for this exercise.
        simulator = new Simulator( new Model(), new ArrivalEvent(1, new Record()), this );
        
        // Should make all text areas and text fields read only, not just log.
        log.setEditable(false);
        
        // The property listener is needed to ensure that the last n records in a
        // n-line text area are visible in all modification scenarios. The listener 
        // is activated on appendText(), not setText(). Information about this 
        // behaviour is much harder to find than it should be :-(
        // The listener code uses an anonymous class and a wildcard (?) for generic 
        // type specification - topics that you have not yet covered.
        //
        // Note that the scroll bars for the text areas are inactivated when the 
        // simulation is waiting for the next step to be triggered. This is a 
        // consequence of the modal nature of alerts - when a modal control is
        // activated, all other controls in the GUI are inactivated.

        log.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,Object newValue) {
                // Scroll to the bottom; use Double.MIN_VALUE to scroll to the top
                log.setScrollTop(Double.MAX_VALUE);
            }
        });
    }

    
    private void clear() {
        time.setText("");
        lastEvent.setText("");
        eventQueue.setText("");
        shoppingArea.setText("");
        checkoutQueue.setText("");
        serviceArea.setText("");
        log.setText("");
        entered.setText("");
        notEntered.setText("");
        
        duration.setText("");
        stepMode.setSelected(false);
    }
  
    @Override
    public void update( SimulationState state ) {
        time.setText(state.getTime());
        lastEvent.setText(state.getEvent());
        eventQueue.setText(state.getEventQueue());
        ModelState ms = state.getModelState();
        
        // Needed for Phase 0
        if (ms == null) {
            shoppingArea.setText("empty");
            checkoutQueue.setText("empty");
            serviceArea.setText("empty");
            log.setText("empty");
            entered.setText("0");
            notEntered.setText("0");
            return;
        }
        
        shoppingArea.setText(ms.getPeopleShopping());
        checkoutQueue.setText(ms.getPeopleQueuing());
        serviceArea.setText(ms.getPeopleBeingServed());
        log.setText("");
        log.appendText(ms.getLog());
        entered.setText(Integer.toString(ms.getNumberInShop()));
        notEntered.setText(Integer.toString(ms.getNumberTurnedAway()));
    }
    
    @Override
    public void warning(String s ) {
         Alert alert = new Alert( Alert.AlertType.WARNING, s );
         alert.setHeaderText("");
         
         // Block execution until the user responds
         alert.showAndWait();
    }   

    @Override
    public String choose(String q, String a1, String a2) {
        ButtonType b1 = new ButtonType( a1 );
        ButtonType b2 = new ButtonType( a2 );
        Alert alert = new Alert(Alert.AlertType.NONE, q, b1, b2 );
        alert.setTitle( "Choose" );
        
        // Block execution until the user responds
        java.util.Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == b1 )
            return a1;
        return a2;
    }    
}

