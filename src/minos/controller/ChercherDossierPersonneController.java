package minos.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import minos.model.bean.Dossier;
import minos.model.bean.Jugement;
import minos.model.bean.Personne;
import minos.model.bean.Requete;
import minos.model.dao.DossierDAO;
import minos.model.dao.PersonneDAO;
import minos.model.service.DossierService;

public class ChercherDossierPersonneController implements Initializable {
	private MainController mainController;

	private DossierDAO dossierDAO;
	private PersonneDAO personneDAO;

	@FXML
	private Button btnCharger;
	@FXML
	private Button btnAnnuler;

	@FXML
	private TableView<LigneTable> table;
	@FXML
	private TableColumn<LigneTable, String> colId;
	@FXML
	private TableColumn<LigneTable, String> colDateRequete;
	@FXML
	private TableColumn<LigneTable, String> colNom;
	@FXML
	private TableColumn<LigneTable, String> colPrenom;
	@FXML
	private TableColumn<LigneTable, String> colDateJugement;

	private ObservableList<LigneTable> tableData = FXCollections.observableArrayList();

	private LigneTable ligneSelectionnee;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dossierDAO = new DossierDAO();
		personneDAO = new PersonneDAO();

		colId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LigneTable, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<LigneTable, String> param) {
				return new SimpleStringProperty(Long.toString(param.getValue().getDossier().getId()));
			}
		});
		colDateRequete.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LigneTable, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<LigneTable, String> param) {
				Dossier dossier = param.getValue().getDossier();
				Requete derniereRequete = DossierService.derniereRequete(dossier);
				return new SimpleStringProperty(derniereRequete.getDateEffet().toString());
			}
		});
		colNom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LigneTable, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<LigneTable, String> param) {
				return new SimpleStringProperty(param.getValue().getPersonne().getNom());
			}
		});
		colPrenom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LigneTable, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<LigneTable, String> param) {
				return new SimpleStringProperty(param.getValue().getPersonne().getPrenom());
			}
		});
		colDateJugement.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LigneTable, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<LigneTable, String> param) {
				Dossier dossier = param.getValue().getDossier();
				Jugement jugementDernier = DossierService.dernierJugement(dossier);
				if (jugementDernier != null) {
					return new SimpleStringProperty(jugementDernier.getDateEffet().toString());
				} else {
					return new SimpleStringProperty("");
				}
			}
		});

		remplirTableData();

		table.setItems(tableData);
		table.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				updateTableSelection();
			}

		});
	}

	private void remplirTableData() {
		tableData.clear();
		for (Dossier dossier : dossierDAO.tousLesDossiers()) {
			if (dossier.getRequetes().isEmpty()) {
				System.err.println("Dossier n°" + dossier.getId() + " n'a pas de requete (ce n'est pas normal)");
			} else {
				Requete premiereRequete = dossier.getRequetes().iterator().next();
				long idRequerant = premiereRequete.getIdRequerant(); // toujours le meme pour l'instant
				Personne personne = personneDAO.find(idRequerant);
				tableData.add(new LigneTable(dossier, personne));
			}

		}
	}

	private void updateTableSelection() {
		ligneSelectionnee = table.getSelectionModel().getSelectedItem();
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	@FXML
	public void chargerDossier() {
		Dossier dossierSelectionne = ligneSelectionnee.getDossier();
		mainController.setDossier(dossierSelectionne);
		Stage stage = (Stage) btnCharger.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void annuler() {
		Stage stage = (Stage) btnAnnuler.getScene().getWindow();
		stage.close();
	}

	private static class LigneTable {
		private Dossier dossier;
		private Personne personne;

		public LigneTable(Dossier dossier, Personne personne) {
			this.dossier = dossier;
			this.personne = personne;
		}

		public Dossier getDossier() {
			return dossier;
		}

		public Personne getPersonne() {
			return personne;
		}
	}
}
