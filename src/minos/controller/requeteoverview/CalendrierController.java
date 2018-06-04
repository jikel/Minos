package minos.controller.requeteoverview;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import minos.model.bean.Adresse;
import minos.model.bean.Dossier;
import minos.model.bean.RendezVous;
import minos.model.bean.Requete;
import minos.model.bean.RoleAdresse;
import minos.model.dao.AdresseDAO;
import minos.model.dao.DossierDAO;
import minos.model.dao.RendezVousDAO;
import minos.model.dao.RoleAdresseDAO;
import minos.model.service.DossierService;
import minos.view.utils.MinosDateFormatter;

public class CalendrierController implements Initializable {

	private ObservableList<RendezVous> rendezVousData = FXCollections.observableArrayList();
	@FXML
	private TableView<RendezVous> rendezVousTable;
	@FXML
	private TableColumn<RendezVous, String> colType;
	@FXML
	private TableColumn<RendezVous, String> colDateRendezVous;
	
	@FXML
	private DatePicker dpChoixDate;
	@FXML
	private Button btnAjouter;
	@FXML
	private Button btnSupprimer;
	private Requete requete;
	private Dossier dossier;
	
	
	private RendezVousDAO rendezVousDAO;
	private DossierDAO dossierDAO;
	private RoleAdresseDAO roleAdresseDAO;
	private AdresseDAO adresseDAO;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rendezVousDAO = new RendezVousDAO();
		dossierDAO = new DossierDAO();
		
		colType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RendezVous,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<RendezVous, String> param) {
				return new SimpleStringProperty(param.getValue().getRoleAdresse().getNom());
			}
		});
		
		colDateRendezVous.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RendezVous,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<RendezVous, String> param) {
				String dateAAfficher = MinosDateFormatter.format(param.getValue().getDateHeure());
				return new SimpleStringProperty(dateAAfficher);
			}
		});
		rendezVousTable.setItems(rendezVousData);
	}
	
	@FXML
	public void selectionnerDate(ActionEvent event){
		System.out.println("date selectionnee");
	}
	
	@FXML
	public void ajouterRdv(){
		System.out.println("ajout RDV");
		RoleAdresse tribunal = DossierService.tribunalCourant(dossier);
		if (dpChoixDate.getValue() != null){
			RendezVous rendezVous = new RendezVous(dossier.getId(), tribunal, LocalDateTime.of(dpChoixDate.getValue(), LocalTime.of(14, 30)));
			rendezVous = rendezVousDAO.create(rendezVous);
		}
		
		
		rafraichirRendezVous();
	}
	
	@FXML
	public void supprimerRdv() {
		System.out.println("suppression RDV");
	
		RendezVous rendezVousSelectionne = rendezVousTable.getSelectionModel().getSelectedItem();

		if(rendezVousSelectionne != null){
			rendezVousDAO.delete(rendezVousSelectionne);
		}
		
		rafraichirRendezVous();
	}
	
	public void setDossier(Dossier dossier){
		this.dossier = dossier;
		rafraichirRendezVous();
	}

	public void setRequete(Requete requete) {
		this.requete = requete;
	}
	
	public void rafraichirRendezVous(){
		dossier = dossierDAO.find(dossier.getId());
		// normalement il y a la methode updateInfo de PersonneOverview mais ce n'est pas necessaire normalemement
		rendezVousData.clear();
		for(RendezVous rendezVous: dossier.getRendezVous()){
			rendezVousData.add(rendezVous);
		}
		rendezVousTable.refresh();
	}

}
