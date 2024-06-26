package mygroup.presentation.GetSeanceFromCalendar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Duration;
import mygroup.metier.Gestionnaire.GestionnaireSeance;
import mygroup.metier.POJO.POJOSeance;
import mygroup.presentation.NewProjet.AddProjetController;
import mygroup.presentation.projet_detail.ProjetDetailController;
import mygroup.presentation.seance_ajoute.SceanceAjouteController;

public class GetSeanceFromCalenderController {
    private String dateSeance;
    private GestionnaireSeance gestionnaireSeance;
    private ObservableList<ItemSeance> listTasItems;
    private GetSeanceFromCalendar getSeanceFromCalendar;
    private AddProjetController addProjetController;
    private ProjetDetailController projetDetailController;

    public GetSeanceFromCalenderController(GetSeanceFromCalendar getSeanceFromCalendar,
            AddProjetController addProjetController) {
        this.addProjetController = addProjetController;
        this.gestionnaireSeance = new GestionnaireSeance();
        this.getSeanceFromCalendar = getSeanceFromCalendar;
        this.dateSeance = this.getSeanceFromCalendar.getDateSeance();
    }

    public GetSeanceFromCalenderController(GetSeanceFromCalendar getSeanceFromCalendar2,
            ProjetDetailController projetDetailController) {
        this.projetDetailController = projetDetailController;
        System.out.println("ProjetDetailController nest pas nul dans le constructeur : " + projetDetailController);
        this.gestionnaireSeance = new GestionnaireSeance();
        this.getSeanceFromCalendar = getSeanceFromCalendar2;
        this.dateSeance = this.getSeanceFromCalendar.getDateSeance();

    }

    public void handleConfirmButton(ActionEvent event, SceanceAjouteController sceanceAjouteController) {
        System.out.println("Confirm Button Clicked");
        System.out.println("Selected events : ");
        for (ItemSeance item : listTasItems) {
            if (item.isSelected()) {
                try {
                    POJOSeance seance = new POJOSeance();
                    seance.setTitre(item.getTitle());
                    seance.setDescription(item.getDescription());

                    // date debut
                    String[] dateDebut = item.getStartDate().split(" ");
                    seance.setDateDebut(dateDebut[0]);
                    seance.setHeureDebut(dateDebut[1]);
                    // date fin
                    String[] dateFin = item.getEndDate().split(" ");
                    seance.setDateFin(dateFin[0]);
                    seance.setHeureFin(dateFin[1]);

                    // create seance
                    this.gestionnaireSeance.setSeance(seance);
                    gestionnaireSeance.createSeance();
                    alert("Seance ajoutée", "La tâche a été ajoutée avec succès");

                    if(this.addProjetController != null )
                     {
                    this.addProjetController.addSeance(getLastSeance());
                    this.addProjetController.displaySeances();
                    sceanceAjouteController.closeWindow();
                    this.handleCancelButtonAction(event);

                      }else if(this.projetDetailController != null){
                        System.out.println("ProjetDetailController nest pas nul dans le handleConfirmButton : " + projetDetailController);
                        this.projetDetailController.addSeance(getLastSeance());
                        System.out.println(getLastSeance());
                        
                        this.projetDetailController.addSeanceFromProjet(getLastSeance());
                        this.projetDetailController.displayedSeances();
                        sceanceAjouteController.closeWindow();
                        this.handleCancelButtonAction(event);
                      }

                } catch (Exception e) {
                    alert("Erreur", "Une erreur s'est produite lors de l'ajout de la seance");
                    e.printStackTrace();
                }
            }
        }

    }

    public LinkedHashMap<String, String> getLastSeance() {
        return gestionnaireSeance.getLastSeance();
    }

    public void handleCancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    public ObservableList<ItemSeance> getDataSeance() {
        GetSeanceFromCalendarModel model = new GetSeanceFromCalendarModel();
        listTasItems = model.getDataFromGoogle(this.dateSeance);
        return listTasItems;
    }

    public LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        return dateTime;
    }

    public void alert(String titre, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();

        // Créer une pause de 1 secondes avant de fermer l'alerte
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> alert.close()); // Utilise close() pour fermer l'alerte
        delay.play();
    }

}
