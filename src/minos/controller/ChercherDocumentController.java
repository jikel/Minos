package minos.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import minos.model.bean.DocumentMinos;
import minos.recherche.MinosIndex;

public class ChercherDocumentController implements Initializable {

	@FXML
	private TextField champRecherche;
	
	@FXML
	private Button btnChercher;
	
	@FXML
	private TableView<DocumentMinos>  tableResult;
	
	private ObservableList<DocumentMinos> tableData = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<DocumentMinos, String> colIdDocument;
	
	@FXML
	private TableColumn<DocumentMinos, String> colNomDocument;
	
	@FXML
	private TableColumn<DocumentMinos, String> colIdDossier;
	
	@FXML
	private TableColumn<DocumentMinos, String> colDateReception;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colIdDocument.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DocumentMinos,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<DocumentMinos, String> param) {
				return new SimpleStringProperty(Long.toString(param.getValue().getId()));
			}
		});
		colNomDocument.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DocumentMinos,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<DocumentMinos, String> param) {
				return new SimpleStringProperty(param.getValue().getNom());
			}
		});
		colIdDossier.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DocumentMinos,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<DocumentMinos, String> param) {
				return new SimpleStringProperty("TODO"); // reverse search
			}
		});
		colDateReception.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DocumentMinos,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<DocumentMinos, String> param) {
				return new SimpleStringProperty(param.getValue().getDateReception().toString());
			}
		});
		
		tableResult.setItems(tableData);
	}
	
	@FXML
	public void chercher() {
		String text = champRecherche.getText();
		try {
			Collection<DocumentMinos> resultat = MinosIndex.getInstance().rechercheTexte(text);
			
			tableData.clear();
			tableData.addAll(resultat);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@FXML
	public void indexer() {
		try {
			MinosIndex.getInstance().creerIndexLucene();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
