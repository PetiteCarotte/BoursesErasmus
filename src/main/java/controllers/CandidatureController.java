package controllers;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.Bourse;
import models.Candidature;
import models.ConnexionJDBC;
import models.Etudiant;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CandidatureController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private CheckBox bourse1Checkbox;

    @FXML
    private CheckBox bourse2Checkbox;

    @FXML
    private CheckBox bourse3Checkbox;

    @FXML
    private CheckBox bourse4Checkbox;

    @FXML
    private TextField noteMoyenneField;

    @FXML
    private TextField noteEnseignant1Field;

    @FXML
    private TextField noteEnseignant2Field;

    @FXML
    private Button calculScoreButton;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label sendLabel;

    @FXML
    private Label checkBoxLabel;

    private Etudiant etudiant;

    private int evaluationEnseignant1;
    private int evaluationEnseignant2;

    private int nombreCheckBoxSelectionnees = 0;
    private final int seuilBoursesSelectionnees = 2;

    @FXML
    private void initialize() {
        // Ajouter des écouteurs pour chaque CheckBox
        addListenerCheckBox(bourse1Checkbox);
        addListenerCheckBox(bourse2Checkbox);
        addListenerCheckBox(bourse3Checkbox);
        addListenerCheckBox(bourse4Checkbox);
    }

    private void addListenerCheckBox(CheckBox checkBox) {
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            // Mettre à jour le message
            update();
        });
    }

    private void update() {
        int nombreBoursesSelectionnees = 0;

        // Compter le nombre de bourses sélectionnées
        if (bourse1Checkbox.isSelected()) nombreBoursesSelectionnees++;
        if (bourse2Checkbox.isSelected()) nombreBoursesSelectionnees++;
        if (bourse3Checkbox.isSelected()) nombreBoursesSelectionnees++;
        if (bourse4Checkbox.isSelected()) nombreBoursesSelectionnees++;

        // Afficher le message uniquement si le nombre dépasse le seuil
        if (nombreBoursesSelectionnees > seuilBoursesSelectionnees) {
            checkBoxLabel.setText("     2 bourses maximum !");
        } else {
            checkBoxLabel.setText("");
        }
    }

    @FXML
    private void onCalculScoreButtonClick() {
    try {


        // Récupérer les données des champs
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        double noteMoyenne = Double.parseDouble(noteMoyenneField.getText());
        int evaluationEnseignant1 = Integer.parseInt(noteEnseignant1Field.getText());
        int evaluationEnseignant2 = Integer.parseInt(noteEnseignant2Field.getText());

        // Créer un objet Etudiant
        etudiant = new Etudiant();
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        etudiant.setNoteMoyenne(noteMoyenne);

        // Calculer le score de la candidature
        double scoreCandidature = (noteMoyenne + evaluationEnseignant1 + evaluationEnseignant2) / 3;

        System.out.println("Note moyenne : " + noteMoyenneField.getText());
        System.out.println("Note enseignant 1 : " + noteEnseignant1Field.getText());
        System.out.println("Note enseignant 2 : " + noteEnseignant2Field.getText());

        // Afficher le résultat
        scoreLabel.setText("Score : " + scoreCandidature);
    }catch(Exception e){
        e.printStackTrace();
    }
    }

    private Candidature createCandidature(Etudiant etudiant, int numeroBourse) {
        // Créer et retourner une instance de Candidature avec les données nécessaires
        // (Vous devrez ajuster cela selon la structure de votre application)
        // Assumez que vous avez une classe Bourse qui peut être initialisée avec un numéro.
        return new Candidature(etudiant, new Bourse(numeroBourse), evaluationEnseignant1, evaluationEnseignant2);
    }

    private void printTemporaryMessage(String message, int dureeEnSecondes){
        sendLabel.setText(message);
        PauseTransition pause = new PauseTransition(Duration.seconds(dureeEnSecondes));
        pause.setOnFinished(event -> sendLabel.setText(""));
        pause.play();
    }

    @FXML
    private void onEnvoyerCandidatureButtonClick() {
        try {
            // Récupérer les données des champs
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            double noteMoyenne = Double.parseDouble(noteMoyenneField.getText());
            evaluationEnseignant1 = Integer.parseInt(noteEnseignant1Field.getText());
            evaluationEnseignant2 = Integer.parseInt(noteEnseignant2Field.getText());

            // Créer un objet Etudiant
            etudiant = new Etudiant();
            etudiant.setNom(nom);
            etudiant.setPrenom(prenom);
            etudiant.setNoteMoyenne(noteMoyenne);

            // Calculer le score de la candidature
            double scoreCandidature = (noteMoyenne + evaluationEnseignant1 + evaluationEnseignant2) / 3;

            // Envoi des données à la base de données
            Connection connexion = ConnexionJDBC.obtenirConnexion();

            // Utilisez la connexion pour insérer les données dans la base de données
            // (Ajoutez votre logique d'insertion ici)

            // Fermer la connexion
            ConnexionJDBC.fermerConnexion(connexion);

            // Réinitialiser les champs de l'interface
            nomField.clear();
            prenomField.clear();
            noteMoyenneField.clear();
            noteEnseignant1Field.clear();
            noteEnseignant2Field.clear();

            // Décocher les CheckBox
            bourse1Checkbox.setSelected(false);
            bourse2Checkbox.setSelected(false);
            bourse3Checkbox.setSelected(false);
            bourse4Checkbox.setSelected(false);


            scoreLabel.setText(""); // Réinitialiser le label

            //Afficher que les données ont bien été sauvegardees
            printTemporaryMessage("Candidature enregistrée ! ", 5);
        } catch (NumberFormatException e) {
            System.err.println("Erreur de conversion numérique : " + e.getMessage());
        }
    }

}
