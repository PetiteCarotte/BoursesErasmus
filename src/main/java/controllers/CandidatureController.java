package controllers;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private TextField numEtuField;

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
    private Label scoreLabel;

    @FXML
    private Label sendLabel;

    @FXML
    private Label checkBoxLabel;

    @FXML
    private Label etuLabel;

    @FXML
    private Label boursesLabel;

    @FXML
    private Label EnvoiLabel;

    @FXML
    private ComboBox ChoixEnsei1;
    @FXML
    private ComboBox ChoixEnsei2;
    @FXML
    private ComboBox ChoixEnsei3;
    @FXML
    private ComboBox ChoixEnsei4;

    private Etudiant etudiant;
    private Candidature candidature;

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
    }

    private void addListenerCheckBox(CheckBox checkBox) {
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            update();
        });
    }

    private void update() {
        int nombreBoursesSelectionnees = 0;

        // Compter le nombre de bourses sélectionnées
        if (bourse1Checkbox.isSelected()) nombreBoursesSelectionnees++;
        if (bourse2Checkbox.isSelected()) nombreBoursesSelectionnees++;
        if (bourse3Checkbox.isSelected()) nombreBoursesSelectionnees++;

        // Afficher le message uniquement si le nombre dépasse le seuil
        if (nombreBoursesSelectionnees > seuilBoursesSelectionnees) {
            checkBoxLabel.setText("     2 bourses maximum !");
        } else {
            checkBoxLabel.setText("");
        }
    }

    private Candidature createCandidature(Etudiant etudiant, int numeroBourse) {
        // Créer et retourner une instance de Candidature avec les données nécessaires
        // (Vous devrez ajuster cela selon la structure de votre application)
        // Assumez que vous avez une classe Bourse qui peut être initialisée avec un numéro.
        return new Candidature(etudiant, new Bourse(numeroBourse), evaluationEnseignant1, evaluationEnseignant2);
    }

    private void printTemporaryMessage(String message, int dureeEnSecondes){
        etuLabel.setText(message);
        PauseTransition pause = new PauseTransition(Duration.seconds(dureeEnSecondes));
        pause.setOnFinished(event -> etuLabel.setText(""));
        pause.play();
    }

    @FXML
    private void onEnvoyerCandidatureButtonClick() {
        try {
            // Récupérer les données des champs
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            double numeroEtu = Double.parseDouble(numEtuField.getText());
            double noteMoyenne = Double.parseDouble(noteMoyenneField.getText());


            // Créer un objet Etudiant
            etudiant = new Etudiant();
            //etudiant.setId(Long.valueOf(1));
            etudiant.setNom(nom);
            etudiant.setPrenom(prenom);
            etudiant.setNumeroEtudiant(numeroEtu);
            etudiant.setNoteMoyenne(noteMoyenne);

            Connection connexion = ConnexionJDBC.getConnexion();

            etudiant.insertIntoDatabase();

            ConnexionJDBC.fermerConnexion(connexion);
            /*
            // Calculer le score de la candidature
            double scoreCandidature = (noteMoyenne + evaluationEnseignant1 + evaluationEnseignant2) / 3;

            // Envoi des données à la base de données
            Connection connexion = ConnexionJDBC.getConnexion();

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


            scoreLabel.setText(""); // Réinitialiser le label*/

            //Afficher que les données ont bien été sauvegardees
            printTemporaryMessage("Candidature enregistrée ! ", 5);
        } catch (NumberFormatException e) {
            System.err.println("Erreur de conversion numérique : " + e.getMessage());
        }
    }

    @FXML
    private void onCalculScoreButtonClick() {
        try {
            // Récupérer les données des champs
            double noteMoyenne = Double.parseDouble(noteMoyenneField.getText());
            int evaluationEnseignant1 = Integer.parseInt(noteEnseignant1Field.getText());
            int evaluationEnseignant2 = Integer.parseInt(noteEnseignant2Field.getText());

            candidature = new Candidature();
            candidature.setEvaluationEnseignant1(evaluationEnseignant1);
            candidature.setEvaluationEnseignant2(evaluationEnseignant2);

            Connection connexion = ConnexionJDBC.getConnexion();

            candidature.insertIntoDatabase();

            ConnexionJDBC.fermerConnexion(connexion);

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

    @FXML
    private void onEnvoyerChoixBoursesButtonClick(){

    }
    @FXML
    private void onFinalizeButtonClick(){

    }
}
