package presentation.NewProjet;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import presentation.projets.ProjetsFormController;

public class AddProjetView {
    private BorderPane root;
    private Button Enregistrer;
    private Button Annuler;
    private TextField TitreField;
    private TextArea ZoneDescription;
    private GridPane ZoneTaches;

    private DatePicker dateDebut;
    private DatePicker dateFin;
    private TextField TempsDebut;
    private TextField TempsFin;

    private AddProjetController controleur;
    private ProjetsFormController projetFormController;
    private Button ajouterTacheButton;
    private ComboBox<String> comboBox1;
    private ComboBox<String> comboBox2;

    public AddProjetView(ProjetsFormController projetFormController) {
        this.projetFormController = projetFormController;
        this.controleur = new AddProjetController(this);
        init();
        style();
        action();
    }

    public AddProjetView(AddProjetController controleur, ProjetsFormController projetFormController) {
        this.projetFormController = projetFormController;
        this.controleur = controleur;
        init();
        style();
        action();
        this.controleur.updateView(this);
        this.controleur.displayTasks(ZoneTaches);
    }

    public void start(Stage primaryStage) {
        StackPane containerContent = createMainContent();
        root = createBorderPane(containerContent);

        Scene scene = new Scene(root, 800, 450);
        scene.getStylesheets().add(getClass().getResource("AddProjetStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Formulaire d'un Projet");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void init() {
        Enregistrer = createButtonWithIcon("Enregistrer", "file:./Pictures/save.png", 13, 13);
        Annuler = createButtonWithIcon("Annuler", "file:./Pictures/annuler.png", 20, 20);
        TitreField = createTextField("");
        ZoneDescription = createTextArea("", "ZoneDescription-Style");
        ZoneTaches = creatZone();
        ajouterTacheButton = createButtonWithIcon("Ajouter Tache", "file:./Pictures/addIcon.png", 20, 20);
        TempsDebut = createTextField();
        TempsFin = createTextField();
        dateDebut = DateTache();
        dateFin = DateTache();
    }

    private void style() {
        Enregistrer.getStyleClass().add("footBtn-style");
        Annuler.getStyleClass().add("footBtn-style");
        ajouterTacheButton.getStyleClass().add("AjouterTache-style");
    }

    private BorderPane createBorderPane(StackPane container) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: white;");
        root.setCenter(container);
        return root;
    }

    private StackPane createMainContent() {
        StackPane container = new StackPane();
        container.getStyleClass().add("container");
        container.setPadding(new Insets(15, 70, 20, 55));

        VBox mainContentContainer = CreateVbox(20, Pos.TOP_CENTER);

        HBox dateDebutContainer = CreateHbox(8, Pos.TOP_LEFT);
        Label labelDateDebut = createLabel("Date de debut : ");
        dateDebutContainer.setPadding(new Insets(0, 0, 0, 0));
        dateDebutContainer.getChildren().addAll(labelDateDebut, dateDebut, TempsDebut);

        HBox dateFinContainer = CreateHbox(8, Pos.TOP_LEFT);
        Label labelDateFin = createLabel("Date de fin : ");
        dateFinContainer.getChildren().addAll(labelDateFin, dateFin, TempsFin);

        HBox ContenaireDates = CreateHbox(8, Pos.TOP_LEFT);
        ContenaireDates.setPadding(new Insets(0, 0, 0, 0));
        ContenaireDates.getChildren().addAll(dateDebutContainer, dateFinContainer);
        mainContentContainer.getChildren().addAll(ContenaireDates);

        HBox ContenaireComboBox = CreateHbox(8, Pos.TOP_RIGHT);
        ContenaireComboBox.getChildren().addAll(comboBox1, comboBox2);

        HBox topContainer = CreateHbox(10, Pos.TOP_LEFT);
        Label labelTitre = createLabel("Titre :  ");
        HBox ContainerTitle = CreateHbox(0, Pos.TOP_LEFT);
        ContainerTitle.setPadding(new Insets(0, 100, 0, 0));
        TitreField.setPadding(new Insets(4, 4, 4, 12));
        ContainerTitle.getChildren().addAll(labelTitre, TitreField);
        topContainer.getChildren().addAll(ContainerTitle, ContenaireComboBox);
        mainContentContainer.getChildren().addAll(topContainer);

        Label labelDescription = createLabel("Discription");
        VBox ContenaireDescription = CreateVbox(10, Pos.TOP_LEFT);
        ContenaireDescription.getChildren().addAll(labelDescription, ZoneDescription);
        mainContentContainer.getChildren().addAll(ContenaireDescription);

        VBox CentenaireTaches = CreateVbox(10, Pos.TOP_LEFT);
        VBox Taches = createTacheBox();
        CentenaireTaches.getChildren().addAll(Taches);

        VBox CentenaireSeances = CreateVbox(10, Pos.TOP_LEFT);
        VBox Seances = createSeanceBox();
        CentenaireSeances.getChildren().addAll(Seances);

        VBox CentenaireDocuments = CreateVbox(10, Pos.TOP_LEFT);
        VBox Documents = createDocumentBox();
        CentenaireDocuments.getChildren().addAll(Documents);

        HBox ContenaireAjout = CreateHbox(8, Pos.TOP_CENTER);
        ContenaireAjout.getChildren().addAll(CentenaireTaches,CentenaireSeances,CentenaireDocuments);
        

        mainContentContainer.getChildren().addAll(ContenaireAjout);

        HBox ContenaireButtons = CreateHbox(8, Pos.TOP_CENTER);
        ContenaireButtons.getChildren().addAll(Enregistrer, Annuler);
        mainContentContainer.getChildren().addAll(ContenaireButtons);

        container.getChildren().addAll(mainContentContainer);

        return container;
    }

    private GridPane creatZone() {
        GridPane grid = new GridPane();
        grid.setVgap(8);
        grid.setHgap(10);
        grid.getStyleClass().addAll("Zone-taches");
        return grid;
    }


    private Button createButtonWithIcon(String name, String path, int i, int j) {
        Button button = new Button(name);
        try {
            Image icon = new Image(path);
            ImageView iconView = new ImageView(icon);
            iconView.setFitWidth(i);
            iconView.setFitHeight(j);
            button.setGraphic(iconView);
        } catch (Exception e) {
            System.out.println("Error loading the icon: " + e.getMessage());
        }
        return button;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("Label-style");
        return label;
    }

    private TextArea createTextArea(String promptText, String style) {
        TextArea Zone = new TextArea();
        Zone.setWrapText(true);
        Zone.setPromptText(promptText);
        Zone.getStyleClass().add(style);

        return Zone;
    }

    private TextField createTextField(String prompt) {
        TextField text = new TextField();
        text.getStyleClass().add("ZoneTitre-Style");
        text.setPromptText(prompt);
        return text;
    }

    private TextField createTextField() {
        TextField text = new TextField();
        text.setPromptText("HH:MM");
        text.setPrefWidth(50);
        return text;
    }

    private DatePicker DateTache() {
        DatePicker date = new DatePicker();
        date.setPrefWidth(100);
        date.promptTextProperty().set("YYYY-MM-DD");
        return date;
    }

    private VBox CreateVbox(int Spacing, Pos position) {
        VBox vbox = new VBox();
        vbox.setSpacing(Spacing);
        vbox.setAlignment(position);
        return vbox;
    }

    private HBox CreateHbox(int Spacing, Pos position) {
        HBox hbox = new HBox();
        hbox.setSpacing(Spacing);
        hbox.setAlignment(position);
        return hbox;
    }

    private VBox createTacheBox() {
        ScrollPane scrollTask = createScrollPane(ZoneTaches);
        scrollTask.getStyleClass().add("scroll-Style");
        VBox contenaireTaches = CreateVbox(5, Pos.TOP_LEFT);
        contenaireTaches.getStyleClass().add("contenaire-taches");
        contenaireTaches.getChildren().addAll(scrollTask, ajouterTacheButton);
        contenaireTaches.setPadding(new Insets(10, 10, 5, 10));
        return contenaireTaches;
    }

    private ScrollPane createScrollPane(GridPane gridPane) {
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return scrollPane;
    }

    // actions :
    public void action() {
        Annuler.setOnAction(event -> {
            try {
                this.controleur.closerWindow(event);
            } catch (Exception e) {
                System.out.println("Erreur pendant la fermeture AddList  : " + e.getMessage());
            }
        });

        Enregistrer.setOnAction(event -> {
            try {
                this.controleur.saveInfosProjet(event);
                this.projetFormController.displayProjets(false);
                this.controleur.closerWindow(event);
            } catch (Exception e) {
                System.out.println("Erreur pendant la fermeture du addlist : " + e.getMessage());
            }
        });

        ajouterTacheButton.setOnAction(event -> {
            this.controleur.getTasksView(event, this.projetFormController);
        });

    }

    public String getTitre() {
        return TitreField.getText();
    }

    public void setTitre(String newTitle) {
        TitreField.setText(newTitle);
    }

    public String getDescription() {
        return ZoneDescription.getText();
    }

    public String getCategorie() {
        return comboBox2.getValue();
    }

    public String getType() {
        return comboBox1.getValue();
    }

    public String getDateDebut() {
        return dateDebut.getValue().toString() + " " + TempsDebut.getText();
    }

    public String getDateFin() {
        return dateFin.getValue().toString() + " " + TempsFin.getText();
    }

    public void setDescription(String newDescription) {
        ZoneDescription.setText(newDescription);
    }

    public GridPane getZoneTaches() {
        return ZoneTaches;
    }

    public void setZoneTaches(GridPane newZoneTaches) {
        ZoneTaches = newZoneTaches;
    }
}


