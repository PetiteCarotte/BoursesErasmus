package controllers;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.*;

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
    private TextField numEtuField;

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
        //addListenerCheckBox(bourse1Checkbox);
        //addListenerCheckBox(bourse2Checkbox);
        //addListenerCheckBox(bourse3Checkbox);
    }

    private void addListenerCheckBox(CheckBox checkBox) {
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            update();
        });
    }

    private void update() {
        /*int nombreBoursesSelectionnees = 0;

        // Compter le nombre de bourses sélectionnées
        if (bourse1Checkbox.isSelected()) nombreBoursesSelectionnees++;
        if (bourse2Checkbox.isSelected()) nombreBoursesSelectionnees++;
        if (bourse3Checkbox.isSelected()) nombreBoursesSelectionnees++;

        // Afficher le message uniquement si le nombre dépasse le seuil
        if (nombreBoursesSelectionnees > seuilBoursesSelectionnees) {
            checkBoxLabel.setText("     2 bourses maximum !");
        } else {
            checkBoxLabel.setText("");
        }*/
    }

    private Candidature createCandidature(long etudiantId, long BourseId) {
        return new Candidature(etudiantId, BourseId, evaluationEnseignant1, evaluationEnseignant2);
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
            etudiant.setNom(nom);
            etudiant.setPrenom(prenom);
            etudiant.setNumeroEtudiant(numeroEtu);
            etudiant.setNoteMoyenne(noteMoyenne);
            List<Enseignement> planEnseignements = new ArrayList<>();

            Candidature canditaure = createCandidature(etudiant.getId(), 1);
            //Bourse bourse = new Bourse();

            Connection connexion = ConnexionJDBC.getConnexion();

            etudiant.insertIntoDatabase();

            ConnexionJDBC.fermerConnexion(connexion);
            /*
            // Calculer le score de la candidature
            double scoreCandidature = (noteMoyenne + evaluationEnseignant1 + evaluationEnseignant2) / 3;

            //Créer un objet candidature


            // Envoi des données à la base de données
            //Connection connexion = ConnexionJDBC.getConnexion();

            //etudiant.insertIntoDatabase();


            //ConnexionJDBC.fermerConnexion(connexion);

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
/*
         if (bourse1Checkbox.isSelected()){
            Bourse bourse1 = new Bourse();
            bourse1.setId(1);
            bourse1.setDestination("Allemagne");
            bourse1.setPostesDisponibles(2);
            bourse1.setResponsableLocal("M.Pierre");

            Candidature canditaure = createCandidature(etudiant, 1, planEnseignements);

        }
        if (bourse2Checkbox.isSelected()){
            Bourse bourse2 = new Bourse();
            bourse2.setId(2);
            bourse2.setDestination("Pologne");
            bourse2.setPostesDisponibles(1);
            bourse2.setResponsableLocal("M.Paul");

            Candidature canditaure = createCandidature(etudiant, 2, planEnseignements);

        }
        if (bourse3Checkbox.isSelected()){
            Bourse bourse3 = new Bourse();
            bourse3.setId(3);
            bourse3.setDestination("Finlande");
            bourse3.setPostesDisponibles(4);
            bourse3.setResponsableLocal("M.Jacques");

            Candidature canditaure = createCandidature(etudiant, 3, planEnseignements);

        }*/

    }
    @FXML
    private void onFinalizeButtonClick(){
        try {
            Connection connexion = ConnexionJDBC.getConnexion();
            // Récupérer les données des champs
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            double numeroEtu = Double.parseDouble(numEtuField.getText());
            double noteMoyenne = Double.parseDouble(noteMoyenneField.getText());


            // Récupérer les données des champs
            int evaluationEnseignant1 = Integer.parseInt(noteEnseignant1Field.getText());
            int evaluationEnseignant2 = Integer.parseInt(noteEnseignant2Field.getText());

            candidature = new Candidature();
            candidature.setEvaluationEnseignant1(evaluationEnseignant1);
            candidature.setEvaluationEnseignant2(evaluationEnseignant2);

            // Créer un objet Etudiant
            etudiant = new Etudiant();
            etudiant.setNom(nom);
            etudiant.setPrenom(prenom);
            etudiant.setNumeroEtudiant(numeroEtu);
            etudiant.setNoteMoyenne(noteMoyenne);
            List<Enseignement> planEnseignements = new ArrayList<>();

            if (bourse1Checkbox.isSelected()){
                etudiant = new Etudiant();
                etudiant.setId(Long.valueOf(1));
                etudiant.setNom(nom);
                etudiant.setPrenom(prenom);
                etudiant.setNumeroEtudiant(numeroEtu);
                etudiant.setNoteMoyenne(noteMoyenne);

                etudiant.insertIntoDatabase();

                Bourse bourse1 = new Bourse();
                bourse1.setId(1);
                bourse1.setDestination("Allemagne");
                bourse1.setPostesDisponibles(2);
                bourse1.setResponsableLocal("M.Pierre");

                bourse1.insertIntoDatabase();

                Candidature candidature = createCandidature(etudiant.getId(), bourse1.getId());
                candidature.insertIntoDatabase();

            }
            if (bourse2Checkbox.isSelected()){
                Bourse bourse2 = new Bourse();
                bourse2.setId(2);
                bourse2.setDestination("Pologne");
                bourse2.setPostesDisponibles(1);
                bourse2.setResponsableLocal("M.Paul");

                bourse2.insertIntoDatabase();
                Candidature candidature = createCandidature(etudiant.getId(), bourse2.getId());
                candidature.insertIntoDatabase();

            }
            if (bourse3Checkbox.isSelected()){
                Bourse bourse3 = new Bourse();
                bourse3.setId(3);
                bourse3.setDestination("Finlande");
                bourse3.setPostesDisponibles(4);
                bourse3.setResponsableLocal("M.Jacques");

                bourse3.insertIntoDatabase();
                Candidature candidature = createCandidature(etudiant.getId(), bourse3.getId());
                candidature.insertIntoDatabase();

            }


            etudiant.insertIntoDatabase();
            //candidature.insertIntoDatabase();

            ConnexionJDBC.fermerConnexion(connexion);

            printTemporaryMessage("Candidature enregistrée ! ", 5);

            // Calculer le score de la candidature
            double scoreCandidature = (noteMoyenne + evaluationEnseignant1 + evaluationEnseignant2) / 3;

            System.out.println("Note moyenne : " + noteMoyenneField.getText());
            System.out.println("Note enseignant 1 : " + noteEnseignant1Field.getText());
            System.out.println("Note enseignant 2 : " + noteEnseignant2Field.getText());

            // Afficher le résultat
            scoreLabel.setText("Score : " + scoreCandidature);
        } catch (NumberFormatException e) {
            System.err.println("Erreur de conversion numérique : " + e.getMessage());
        }
    }
}
