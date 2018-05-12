package minos.controller.requeteoverview;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import minos.model.bean.DocumentMinos;
import minos.model.bean.Dossier;
import minos.model.bean.Requete;
import minos.model.bean.TypeDocumentMinos;
import minos.model.dao.DocumentMinosDAO;
import minos.model.dao.DossierDAO;

public class NotesController implements Initializable{

	@FXML
	private TextArea textArea;
	@FXML
	private Button btnEditer;
	@FXML
	private Button btnSauvegarder;
	@FXML
	private Button btnAnnuler;
	private Dossier dossier;
	private DocumentMinosDAO documentDAO;
	private DocumentMinos document;
	private DossierDAO dossierDAO;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textArea.setEditable(false);
		textArea.setDisable(true);
		documentDAO = new DocumentMinosDAO();
		dossierDAO = new DossierDAO();
	}

	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
		document = null;
		for (Long idDocument : dossier.getNomsDocument().keySet()) {
			DocumentMinos documentRecherche = documentDAO.find(idDocument);
			if(TypeDocumentMinos.note.equals(documentRecherche.getType())){
				document = documentRecherche;
				break; // il n'y a qu'une seule note par dossier donc inutile de continuer a chercher
			}
		}
		if (document==null){
			DocumentMinos nouveauDocument = new DocumentMinos("note_"+dossier.getId(), TypeDocumentMinos.note, "".getBytes(), LocalDateTime.now());
			document = documentDAO.create(nouveauDocument, dossier);

		}
		textArea.setText(new String(document.getContenu()));
	}
	
	@FXML
	public void editerTextArea(){
		textArea.setEditable(true);
		textArea.setDisable(false);
	}
	
	@FXML
	public  void sauvegarderTextArea(){
		document.setContenu(textArea.getText().getBytes());
		documentDAO.update(document);
		textArea.setEditable(false);
		textArea.setDisable(true);
	}
	
	@FXML
	public  void annulerTextArea(){
		textArea.setText(new String(document.getContenu()));
		textArea.setEditable(false);
		textArea.setDisable(true);
	}

}
