package minos.controller.requeteoverview;

import java.net.URL;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import minos.model.bean.DocumentMinos;
import minos.model.bean.Dossier;
import minos.model.bean.Requete;
import minos.model.dao.DocumentMinosDAO;

public class CorrespondanceController implements Initializable {
	@FXML
	private Button btnAjoutDoc;
	
	private ObservableList<DocumentMinos> tableData = FXCollections.observableArrayList();
	
	@FXML
	private TableView<DocumentMinos> tableCorrespondances;
	
	@FXML
	private TableColumn<DocumentMinos, String> colNomDocument;
	
	@FXML
	private TableColumn<DocumentMinos, String> colDateDocument;

	private Dossier dossier;
	
	private DocumentMinosDAO documentMinosDAO;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		documentMinosDAO = new DocumentMinosDAO();
		colNomDocument.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DocumentMinos,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<DocumentMinos, String> param) {
				return new SimpleStringProperty(param.getValue().getNom());
			}
		});
		colDateDocument.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DocumentMinos,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<DocumentMinos, String> param) {
				return new SimpleStringProperty(param.getValue().getDateReception().toString());
			}
		});
		tableCorrespondances.setItems(tableData);
	}
	
	@FXML
	public void ajouterDocument(){
		System.out.println("ajouter document a correspondance");
	}

	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
		tableData.clear();
		for (Entry<Long, String> entry : dossier.getNomsDocument().entrySet()) {
			Long idDocument = entry.getKey();
			DocumentMinos document = documentMinosDAO.find(idDocument); //FIXME: ceci charge également le contenu du document!!!
			tableData.add(document);
		}
		tableCorrespondances.refresh();
	}



}
