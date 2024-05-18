package mygroup.persistence.DAO ;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import mygroup.persistence.DBConnection;


public class DAOProjet {
    
    // Create
    public void create(String titre, String description, String categorie, String type, String dateDebut, String dateFin, List<String> taches, List<String> seances) {
        try {
            MongoCollection<Document> collection = DBConnection.getInstance().getDatabase().getCollection("projets");
            Document doc = new Document("titre", titre).append("description", description).append("categorie", categorie)
                            .append("type", type).append("dateDebut", dateDebut).append("dateFin", dateFin).append("taches", taches)
                            .append("seances", seances);
            collection.insertOne(doc);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du projet : " + e.getMessage());
        }
    }
    
    // Read
    public Document read(String id) {
        Document projet = null;
        try {
            MongoCollection<Document> collection = DBConnection.getInstance().getDatabase().getCollection("projets");
            ObjectId objId = new ObjectId(id);
            projet = collection.find(Filters.eq("_id", objId)).first();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération du projet : " + e.getMessage());
        }
        return projet;
    }

    // Update
    public void update(String id, String titre, String description, List<Document> taches) {
        try {
            MongoCollection<Document> collection = DBConnection.getInstance().getDatabase().getCollection("projets");
            Document doc = new Document();
            if (titre != null) {
                doc.append("titre", titre);
            }
            if (description != null) {
                doc.append("description", description);
            }
            if (taches != null) {
                doc.append("taches", taches);
            }
            collection.updateOne(Filters.eq("id", id), new Document("$set", doc));
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour du projet : " + e.getMessage());
        }
    }

    // Delete
    public void delete(String id) {
        try {
            MongoCollection<Document> collection = DBConnection.getInstance().getDatabase()
                            .getCollection("projets");
            ObjectId objId = new ObjectId(id);
            collection.deleteOne(Filters.eq("_id", objId));
        } catch (Exception e) {
                System.err.println("Erreur lors de la suppression de la projet : " + e.getMessage());
        }
    }

    public List<Document> getAllProjects() {
        List<Document> allProjets = new ArrayList<>();
        try {
            MongoCollection<Document> collection = DBConnection.getInstance().getDatabase().getCollection("projets");
            FindIterable<Document> cursor = collection.find();

            for (Document document : cursor) {
                allProjets.add(document);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de la liste des projets : " + e.getMessage());
        }
        return allProjets;
    }

    public LinkedHashMap<String, String> getArchivedProjects() {
        LinkedHashMap<String, String> archivedProjects = new LinkedHashMap<>();
        String currentDate = java.time.LocalDate.now().toString() + " " + java.time.LocalTime.now().toString().substring(0, 5);
        List<Document> allProjets = getAllProjects();
        for (Document projet : allProjets) {
            String dateFin = projet.getString("dateFin");
            if (dateFin.compareTo(currentDate) < 0) {
                String id = projet.getObjectId("_id").toString();
                String titre = projet.getString("titre");
                archivedProjects.put(id, titre);
            }
        }
        return archivedProjects;
    }
}