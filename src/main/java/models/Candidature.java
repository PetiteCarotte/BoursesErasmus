package models;

import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class Candidature {

    private long id;
    private Etudiant etudiant;
    private Bourse bourse;
    private List<Enseignement> planEnseignements;
    private int evaluationEnseignant1;
    private int evaluationEnseignant2;

    public Candidature(long id, Etudiant etudiant, Bourse bourse, List<Enseignement> planEnseignements,
                       int evaluationEnseignant1, int evaluationEnseignant2) {
        this.id = id;
        this.etudiant = etudiant;
        this.bourse = bourse;
        this.planEnseignements = planEnseignements;
        this.evaluationEnseignant1 = evaluationEnseignant1;
        this.evaluationEnseignant2 = evaluationEnseignant2;
    }
    public Candidature(Etudiant etudiant, Bourse bourse, int evaluationEnseignant1, int evaluationEnseignant2) {
        this.etudiant = etudiant;
        this.bourse = bourse;
        this.evaluationEnseignant1 = evaluationEnseignant1;
        this.evaluationEnseignant2 = evaluationEnseignant2;
    }

    public Candidature() {

    }

    // Getters et setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Bourse getBourse() {
        return bourse;
    }

    public void setBourse(Bourse bourse) {
        this.bourse = bourse;
    }

    public List<Enseignement> getPlanEnseignements() {
        return planEnseignements;
    }

    public void setPlanEnseignements(List<Enseignement> planEnseignements) {
        this.planEnseignements = planEnseignements;
    }

    public int getEvaluationEnseignant1() {
        return evaluationEnseignant1;
    }

    public void setEvaluationEnseignant1(int evaluationEnseignant1) {
        this.evaluationEnseignant1 = evaluationEnseignant1;
    }

    public int getEvaluationEnseignant2() {
        return evaluationEnseignant2;
    }

    public void setEvaluationEnseignant2(int evaluationEnseignant2) {
        this.evaluationEnseignant2 = evaluationEnseignant2;
    }

    public double calculerScoreCandidature() {
        return (etudiant.getNoteMoyenne() + evaluationEnseignant1 + evaluationEnseignant2) / 3;
    }

    public void insertIntoDatabase() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get the database connection
            connection = ConnexionJDBC.getConnexion();

            // Prepare the SQL statement
            String insertQuery = "INSERT INTO Candidature (evaluationEnseignant1, evaluationEnseignant2) VALUES (  ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);

            // Set the parameters
            //preparedStatement.setLong(1, id);
            //preparedStatement.setLong(2, etudiant.getId());
            //preparedStatement.setLong(3, bourse.getId());

            // Convert the list of Enseignement to a comma-separated string
            /*String planEnseignementsString = planEnseignements.stream()
                    .map(Enseignement::getNom) // Assuming Enseignement has a 'getNom' method
                    .collect(Collectors.joining(","));*/
            //preparedStatement.setString(1, planEnseignementsString);

            preparedStatement.setInt(1, evaluationEnseignant1);
            preparedStatement.setInt(2, evaluationEnseignant2);

            // Execute the update
            preparedStatement.executeUpdate();

            System.out.println("Candidature inserted into the database successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            ConnexionJDBC.fermerConnexion(connection);
        }
    }

    @Override
    public String toString() {
        return "Candidature{" +
                "id=" + id +
                ", etudiant=" + etudiant +
                ", bourse=" + bourse +
                ", planEnseignements=" + planEnseignements +
                ", evaluationEnseignant1=" + evaluationEnseignant1 +
                ", evaluationEnseignant2=" + evaluationEnseignant2 +
                '}';
    }
}