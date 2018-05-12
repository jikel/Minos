package minos.recherche;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import minos.model.bean.DocumentMinos;
import minos.model.dao.DocumentMinosDAO;

public class MinosIndex {
	
	private static final int TIKA_WRITE_LIMIT = -1; // infini
	
	private static final String INDEX_DIRECTORY_PATH = System.getProperty("user.dir") + "/index/";

	private static final String CHAMP_NOM_FICHIER = "NOM_FICHIER";
	private static final String CHAMP_ID_DOCUMENT_MINOS = "ID_DOCUMENT_MINOS";
	private static final String CHAMP_TYPE_DOCUMENT_MINOS = "TYPE_DOCUMENT_MINOS";
	private static final String CHAMP_CONTENU = "CONTENU";

	private Directory directory;
	private Analyzer analyzer;
	private DocumentMinosDAO documentMinosDAO;
	
	private static MinosIndex instance = new MinosIndex(); // singleton créé au démarrage de l'application
	
	public static MinosIndex getInstance() {
		return instance;
	}

	private MinosIndex() {
		documentMinosDAO = new DocumentMinosDAO();
	}
	
	public void chargerIndexLucene() throws Exception {
		directory = FSDirectory.open(Paths.get(INDEX_DIRECTORY_PATH));
		analyzer = new StandardAnalyzer();
	}
	

	public void creerIndexLucene() throws Exception {
//		directory = new RAMDirectory();
		directory = FSDirectory.open(Paths.get(INDEX_DIRECTORY_PATH));
		analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		config.setOpenMode(OpenMode.CREATE); // écrase le précédent
		IndexWriter indexWriter = new IndexWriter(directory, config);

		for (Long documentId : documentMinosDAO.tousLesDocumentIds()) {
			DocumentMinos documentMinos = documentMinosDAO.find(documentId);
			if (documentMinos.getContenu().length > 0) {
				String contenuTexteDocument = lireContenuDocument(documentMinos.getContenu());

				Document documentLucene = new Document();
				documentLucene.add(new Field(CHAMP_NOM_FICHIER, documentMinos.getNom(), TextField.TYPE_STORED));
				documentLucene.add(new Field(CHAMP_ID_DOCUMENT_MINOS, Long.toString(documentMinos.getId()), TextField.TYPE_STORED));
				documentLucene.add(new Field(CHAMP_TYPE_DOCUMENT_MINOS, documentMinos.getType().toString(), TextField.TYPE_STORED));
				documentLucene.add(new Field(CHAMP_CONTENU, contenuTexteDocument, TextField.TYPE_STORED));

				indexWriter.addDocument(documentLucene);
			}
		}

		indexWriter.close();
	}

	public Collection<DocumentMinos> rechercheTexte(String texteATrouver) throws Exception {
		DirectoryReader ireader = DirectoryReader.open(directory);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		QueryParser queryParser = new QueryParser(CHAMP_CONTENU, analyzer);
		Query query = queryParser.parse(texteATrouver);
		ScoreDoc[] hits = isearcher.search(query, 1000).scoreDocs;

		Collection<DocumentMinos> resultat = new ArrayList<>();
		for (ScoreDoc hit : hits) {
			Document documentLucene = isearcher.doc(hit.doc);
			String idDocumentMinosString = documentLucene.get(CHAMP_ID_DOCUMENT_MINOS);
			long idDocumentMinos = Long.parseLong(idDocumentMinosString);
			DocumentMinos documentMinos = documentMinosDAO.find(idDocumentMinos);
			resultat.add(documentMinos);
		}

		return resultat;
	}

	// via apache tika
	private String lireContenuDocument(byte[] contenu) throws IOException, SAXException, TikaException {
		AutoDetectParser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler(TIKA_WRITE_LIMIT);
		Metadata metadata = new Metadata();

		ByteArrayInputStream inputStream = new ByteArrayInputStream(contenu);
		parser.parse(inputStream, handler, metadata);

		return handler.toString();
	}
}
