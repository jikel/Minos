package minos.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import minos.model.bean.Adresse;
import minos.model.bean.Dossier;
import minos.model.bean.Personne;
import minos.model.bean.Requete;
import minos.model.dao.DossierDAO;
import minos.model.dao.PersonneDAO;
import minos.view.utils.MinosDateFormatter;

public class PersonneOverviewController implements Initializable {

	// info personne
	@FXML
	private Label prenom;
	@FXML
	private Label nom;
	@FXML
	private Label niss;
	@FXML
	private Label adresseGUI;

	// info requetes
	@FXML
	private TableView<Requete> requeteTable;
	@FXML
	private TableColumn<Requete, String> roleColonne;
	@FXML
	private TableColumn<Requete, String> dateColonne;

	@FXML
	private Button btnTransferPersonne;
	@FXML
	public Label idPersonne;

	private Dossier dossier;

	private PersonneDAO personneDAO;

	private ObservableList<Requete> requeteData = FXCollections.observableArrayList();

	private MainController mainController;
	private DossierDAO dossierDAO;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		roleColonne.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Requete, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Requete, String> param) {
						return new SimpleStringProperty(param.getValue().getNumeroRole());
					}
				});
		dateColonne.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Requete, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Requete, String> param) {
						String dateAAfficher = MinosDateFormatter.format(param.getValue().getDateEffet());
						return new SimpleStringProperty(dateAAfficher);
					}
				});
		requeteTable.setItems(requeteData);
		requeteTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				updateTableSelection();
			}
		});
		personneDAO = new PersonneDAO();
		dossierDAO = new DossierDAO();
	}

	private void updateTableSelection() {
		
		Requete selectedItem = requeteTable.getSelectionModel().getSelectedItem();
		if(selectedItem != null){
			mainController.setRequete(selectedItem);
		}
		
	}

	@FXML
	private void ajoutRequete() {
		// ouverture de la fenetre
		AnchorPane nouvelleRequetePane;
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PersonneOverviewController.class.getResource("/minos/view/NouvelleRequeteDialog.fxml"));

			nouvelleRequetePane = (AnchorPane) loader.load();
			
			NouvelleRequeteDialogController nouvelleRequeteDialogController = loader.getController();
			nouvelleRequeteDialogController.setDossier(dossier);
			nouvelleRequeteDialogController.setPersonneOverviewController(this);

			Scene scene = new Scene(nouvelleRequetePane);
			stage.setScene(scene);
			stage.setTitle("Ajout nouvelle requête");
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void btnTransferClicked(ActionEvent event) {
		System.out.println("cliqué");
	}

	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
		rafraichir();
	}

	private void updateInfoPersonne() {
		long idRequerant = dossier.getRequetes().iterator().next().getIdRequerant();
		Personne personne = personneDAO.find(idRequerant);

		Adresse adresse = personne.getAdresse();

		// complete les Label
		prenom.setText(personne.getPrenom());
		nom.setText(personne.getNom());
		niss.setText(personne.getNiss());

		adresseGUI.setText(adresse.getRue() + " " + adresse.getNumero() + " " + adresse.getBoite() + "\n"
				+ adresse.getCodePostal() + "\n " + adresse.getPays());
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public void rafraichir() {
		dossier = dossierDAO.find(dossier.getId());
		updateInfoPersonne();
		requeteData.clear();
		for (Requete requete : dossier.getRequetes()) {
			requeteData.add(requete);
		}
		requeteTable.refresh();
	}

}
