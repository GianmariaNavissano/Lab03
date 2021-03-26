package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> languageBox;

    @FXML
    private TextArea wordsTxt;

    @FXML
    private TextArea resultTxt;

    @FXML
    private Label mistakesTxt;

    @FXML
    private Label timeTxt;
    
    String[] words;
    int mistakes = 0;

    @FXML
    void doCheck(ActionEvent event) {
    	mistakes = 0;
    	if((!wordsTxt.getText().equals("")) && (languageBox.getValue() !=null)) {
    		long start = System.nanoTime();
    		String sTemp = wordsTxt.getText().toLowerCase().replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_()\\[\\]\"]", "");
        	words = sTemp.split(" ");
        	for(String w : words) {
        		if(languageBox.getValue().equals("Italiano")) {
        			if(!model.dichotomicCheckIta(w)) {
        				resultTxt.setText(this.resultTxt.getText()+"\n"+w);
        				mistakes ++;
        			}
        		} else if(languageBox.getValue().equals("English")) {
        			if(!model.dichotomicCheckEng(w)) {
        				resultTxt.setText(this.resultTxt.getText()+"\n"+w);
        				mistakes ++;
        			}
        		}
        	}
        	if(mistakes > 0) {
        		this.mistakesTxt.setText("The text contains "+mistakes+" mistakes");
        	}
        	long end = System.nanoTime();
        	long time = end-start;
        	time = time / (long) 1000000;
        	timeTxt.setText("Spell check completed in "+time+ " milli-seconds");
    	}
    }

    @FXML
    void doClear(ActionEvent event) {
    	this.resultTxt.clear();
    	this.wordsTxt.clear();
    	this.mistakesTxt.setText("");
    }

    public void setModel (Model model) {
    	this.model = model;
    }
    @FXML
    void initialize() {
        assert languageBox != null : "fx:id=\"languageBox\" was not injected: check your FXML file 'Scene.fxml'.";
        assert wordsTxt != null : "fx:id=\"wordsTxt\" was not injected: check your FXML file 'Scene.fxml'.";
        assert resultTxt != null : "fx:id=\"resultTxt\" was not injected: check your FXML file 'Scene.fxml'.";
        assert mistakesTxt != null : "fx:id=\"mistakesTxt\" was not injected: check your FXML file 'Scene.fxml'.";
        assert timeTxt != null : "fx:id=\"timeTxt\" was not injected: check your FXML file 'Scene.fxml'.";
        languageBox.getItems().addAll("Italiano", "English");
    }
}
