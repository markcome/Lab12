package it.polito.tdp.rivers;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.model.SimulationResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RiversController {
	
	private Model model;
	
	public void setModel(Model model) {
		this.model = model;
		
		this.boxRiver.getItems().addAll(this.model.getAllRivers());
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<River> boxRiver;

    @FXML
    private TextField txtStartDate;

    @FXML
    private TextField txtEndDate;

    @FXML
    private TextField txtNumMeasurements;

    @FXML
    private TextField txtFMed;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnSimula;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCompleta(ActionEvent event) {
    	
    	River river = this.boxRiver.getValue();
    	if (river == null) {
    		this.txtResult.setText("Select a river");
    	}
    	this.txtFMed.setText(String.valueOf(river.getMediaFlusso()));
    	this.txtStartDate.setText(river.getPrimaMisurazione().getDay().toString());
    	this.txtEndDate.setText(river.getUltimaMisurazione().getDay().toString());
    	this.txtNumMeasurements.setText(String.valueOf(river.getMisurazioni().size()));
 
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	try {
	    	double k = Double.parseDouble(this.txtK.getText().trim());
	    	River river = this.boxRiver.getValue();
	    			
	    	if(k < 0 || k > 1) {
	    		this.txtResult.setText("k must be between 0 and 1");
	    		return;
	    	}
	    	if(river == null) {
	    		this.txtResult.setText("Select a river");
	    		return;
	    	}
	    	
	    	SimulationResult sr = this.model.simulate(river, k);
	    	
	    	txtResult.setText("Number of days in which it cannot guarantee the minimum capacity: "
					+ sr.getGiorniInsoddisfatti() + "\n");
			txtResult.appendText("Average Capacity: " + sr.getcMed() + "\n");
    	
    	} catch(NumberFormatException nfe) {
    		nfe.printStackTrace();
    		txtResult.setText("Some Error Occured: " + nfe.getMessage());
    	}
    	
    }

    @FXML
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Rivers.fxml'.";

        this.txtResult.setStyle("-fx-font-family: monospace");
    }
}

