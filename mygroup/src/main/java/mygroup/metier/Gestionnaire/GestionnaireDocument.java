package mygroup.metier.Gestionnaire ;

import mygroup.metier.POJO.POJODocument;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.bson.Document;

import mygroup.persistence.DAO.DAODocument;

public class GestionnaireDocument {
    private DAODocument daoDocument;
    private POJODocument pojoDocument;

    public List<Document> getAllDocuments() {
        return daoDocument.getAllDocuments();
    }

    public GestionnaireDocument() {
        this.daoDocument = new DAODocument();
        this.pojoDocument = new POJODocument();
    }

    public DAODocument getDaoDocument() {
        return daoDocument;
    }

    public POJODocument getPojoDocument() {
        return pojoDocument;
    }

    public void setPojoDocument(POJODocument pojoDocument) {
        this.pojoDocument = pojoDocument;
    }

    // getIdLastDoc
    public String getIdLastDoc() {
        return daoDocument.getIdLastDoc();
    }

    // Method to create a new document
    public void creerDocument() {
        daoDocument.create(this.pojoDocument.getTitre(), this.pojoDocument.getDescription(), this.pojoDocument.getURL());
    }

    public boolean isUrlAccessible(String filePath) {
        try {
            if (!filePath.isEmpty()) {
                return Files.exists(Paths.get(filePath)) && Files.isReadable(Paths.get(filePath));
            }
            return false;
        } catch (Exception e) {
            System.out.println("Erreur lors de la vérification du fichier : " + e.getMessage());
            return false;
        }
    }

    public void setProjetId(List<String> documentsIds, String lastProjetId) {
        daoDocument.setProjetId(documentsIds, lastProjetId);
    }

    public Document getDocumentById(String document) {
        return daoDocument.read(document);
    }

}
