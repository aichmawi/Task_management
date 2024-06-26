package mygroup.presentation.login;

import java.text.ParseException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import mygroup.presentation.listes.ListeFormView;
import javafx.scene.Node;

public class LoginController {
    private LoginFormView loginFormView;
    private LoginModel loginModel;

    public LoginController(LoginFormView loginFormView) {
        this.loginFormView = loginFormView;
        this.loginModel = new LoginModel("");
    }

    public void handleLoginButtonClick(ActionEvent event) throws ParseException {
        try {
            String email = this.loginFormView.getEmail();
            this.loginModel.setGmail(email);
            boolean emailIsValid = loginModel.isValidEmailAddress();
            if (emailIsValid) {
                navigateToListView(event);
            } else {
                displayAlert("Erreur", "L'adresse e-mail est incorrecte.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            displayAlert("Erreur", "Une erreur est survenue lors de la validation de l'adresse e-mail.");
        }
    }

    // Navigation vers la vue de liste
    private void navigateToListView(ActionEvent event) {
        ListeFormView nextView = new ListeFormView();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        nextView.start(stage);
    }

    // Afficher une alerte
    public void displayAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
