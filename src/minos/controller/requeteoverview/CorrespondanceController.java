package minos.controller.requeteoverview;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import minos.model.bean.DocumentMinos;
import minos.model.bean.Dossier;
import minos.model.bean.TypeDocumentMinos;
import minos.model.dao.DocumentMinosDAO;
import minos.model.dao.JugementDAO;
import minos.view.utils.MinosDateFormatter;

public class CorrespondanceController implements Initializable {
	@FXML
	private Button btnAjoutDoc;

	@FXML
	private Button btnTelecharger;

	private ObservableList<TypeDocumentMinos> comboData = FXCollections.observableArrayList();

	@FXML
	private ComboBox<TypeDocumentMinos> comboTypeDoc;

	private ObservableList<DocumentMinos> tableData = FXCollections.observableArrayList();

	@FXML
	private TableView<DocumentMinos> tableCorrespondances;

	@FXML
	private TableColumn<DocumentMinos, String> colNomDocument;

	@FXML
	private TableColumn<DocumentMinos, String> colDateDocument;

	@FXML
	private TableColumn<DocumentMinos, String> colType;

	private Dossier dossier;

	private DocumentMinosDAO documentMinosDAO;

	private JugementDAO jugementDAO;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		documentMinosDAO = new DocumentMinosDAO();
		jugementDAO = new JugementDAO();
		colNomDocument.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DocumentMinos, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<DocumentMinos, String> param) {
				return new SimpleStringProperty(param.getValue().getNom());
			}
		});
		colDateDocument.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DocumentMinos, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<DocumentMinos, String> param) {
				String dateAAfficher = MinosDateFormatter.format(param.getValue().getDateReception());
				return new SimpleStringProperty(dateAAfficher);
			}
		});
		colType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DocumentMinos, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<DocumentMinos, String> param) {
				return new SimpleStringProperty(param.getValue().getType().getDbValue());
			}
		});
		tableCorrespondances.setItems(tableData);
		createComboData();
		comboTypeDoc.setItems(comboData);
	}

	private void createComboData() {
		List<TypeDocumentMinos> typesAutorises = Arrays.asList(TypeDocumentMinos.conclusion, TypeDocumentMinos.pieceInventaire, TypeDocumentMinos.rapportAdministratif);
		for (TypeDocumentMinos typeDocumentMinos : typesAutorises) {
			comboData.add(typeDocumentMinos);
		}
	}

	@FXML
	public void ajouterDocument() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Sélectionner document");
		File file = fileChooser.showOpenDialog(btnAjoutDoc.getScene().getWindow());
		if (file != null) {
			byte[] contenuFichier;
			try {
				contenuFichier = Files.readAllBytes(file.toPath());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			TypeDocumentMinos typeDocumentMinos = comboTypeDoc.getSelectionModel().getSelectedItem();
			DocumentMinos document = new DocumentMinos(file.getName(), typeDocumentMinos, contenuFichier, LocalDateTime.now());
			document = documentMinosDAO.create(document, dossier);

			rafraichirTable();
		}
	}

	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
		rafraichirTable();
	}

	private void rafraichirTable() {
		tableData.clear();
		for (Entry<Long, String> entry : dossier.getNomsDocument().entrySet()) {
			Long idDocument = entry.getKey();
			DocumentMinos document = documentMinosDAO.find(idDocument); //FIXME: ceci charge également le contenu du document!!!
			tableData.add(document);
		}
		tableCorrespondances.refresh();
	}

	@FXML
	public void telecharger() {
		DocumentMinos selectedItem = tableCorrespondances.getSelectionModel().getSelectedItem();

		String nomFichier = selectedItem.getNom();
		String extension = "";
		if (nomFichier.contains("."))
			extension = nomFichier.substring(nomFichier.lastIndexOf("."), nomFichier.length());

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Sauvegarder document");
		fileChooser.setInitialFileName(nomFichier);

		if (!extension.isEmpty()) {
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(extension, "*." + extension);
			fileChooser.getExtensionFilters().add(extFilter);
		}

		File file = fileChooser.showSaveDialog(btnTelecharger.getScene().getWindow());
		if (file != null) {
			try {
				Files.write(file.toPath(), selectedItem.getContenu());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
