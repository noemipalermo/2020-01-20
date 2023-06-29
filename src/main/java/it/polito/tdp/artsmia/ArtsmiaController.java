package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Arco;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola artisti connessi");
    	
    	List<Arco> archi = model.getArtistiConnessi();
    	if(archi.isEmpty()) {
    		this.txtResult.appendText("Prima devi creare il grafo");
    	}
    	
    	for(Arco a: archi) {
    		this.txtResult.appendText(a.getA1()+" - "+a.getA2()+" : "+a.getPeso()+"\n");
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso");
    	
    	String input = this.txtArtista.getText();
    	int idArtista = 0;
    	try {
    		idArtista = Integer.parseInt(input);
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Scelgi un valore valido");
    	}
    	
    	if(model.isInGraph(idArtista)==false) {
    		this.txtResult.appendText("Artista non presente nel grafo");
    		return;
    	}
    	
    	List<Integer> percorso = model.calcolaPercorso(idArtista);
    	for(Integer i : percorso) {
    		this.txtResult.appendText(i.toString()+"\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Crea grafo");
    	
    	String role = this.boxRuolo.getValue();
    	if(role==null) {
    		this.txtResult.appendText("Scegli un ruolo.");
    	}
    	
    	model.creagrafo(role);
    	
    	txtResult.appendText(String.format("Grafo creato con %d vertici e %d archi", 
    			this.model.getAllVertex().size(), this.model.getArtistiConnessi().size()));
    	
    	btnCalcolaPercorso.setDisable(false);
    }

    public void setModel(Model model) {
    	this.model = model;
    	List<String> ruoli = model.getAllRoles();
    	this.boxRuolo.getItems().addAll(ruoli);
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
