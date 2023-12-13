package models;

import java.util.List;
import java.util.stream.Collectors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class Candidature {

    private long id;
    private long etudiantId;
    private long bourseId;
    private List<Enseignement> planEnseignements;
    private int evaluationEnseignant1;
    private int evaluationEnseignant2;

    /*
    public Candidature(long id, Etudiant etudiant, Bourse bourse, List<Enseignement> planEnseignements,
                       int evaluationEnseignant1, int evaluationEnseignant2) {
        this.id = id;
        this.etudiant = etudiant;
        this.bourse = bourse;
        this.planEnseignements = planEnseignements;
        this.evaluationEnseignant1 = evaluationEnseignant1;
        this.evaluationEnseignant2 = evaluationEnseignant2;
    }
    public Candidature(Etudiant etudiant, Bourse bourse, List<Enseignement> planEnseignements, int evaluationEnseignant1, int evaluationEnseignant2) {
        this.etudiant = etudiant;
        this.bourse = bourse;
        this.planEnseignements = planEnseignements;
        this.evaluationEnseignant1 = evaluationEnseignant1;
        this.evaluationEnseignant2 = evaluationEnseignant2;
    }*/

    public Candidature(long etuId, long bourseId, List<Enseignement> planEnseignement, int evaluationEnseignant1, int evaluationEnseignant2){
        this.etudiantId = etuId;
    }

    public Candidature(long etuId, long bourseId, int evaluationEnseignant1, int evaluationEnseignant2){
        this.etudiantId = etuId;
    }
   /* public Candidature(Etudiant etudiant, Bourse bourse) {
        this.etudiant = etudiant;
        this.bourse = bourse;
        this.evaluationEnseignant1 = 0;
        this.evaluationEnseignant2 = 0;
    }
*/
    public Candidature() {

    }

    // Getters et setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEtudiant() {
        return etudiantId;
    }

    public void setEtudiant(long etudiant) {
        this.etudiantId = etudiant;
    }

    public long getBourse() {
        return bourseId;
    }

    public void setBourse(long bourse) {
        this.bourseId = bourse;
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
        return ( evaluationEnseignant1 + evaluationEnseignant2) / 3;
    }

    public void insertIntoDatabase() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get the database connection
            connection = ConnexionJDBC.getConnexion();

            // Prepare the SQL statement
            String insertQuery = "INSERT INTO Candidature (evaluationEnseignant1, evaluationEnseignant2) VALUES ( ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);

            // Set the parameters
            //preparedStatement.setLong(1, id);
            //preparedStatement.setLong(2, etudiant.getId());
            //preparedStatement.setLong(3, bourse.getId());

            // Convert the list of Enseignement to a comma-separated string
            //String planEnseignementsString = planEnseignements.stream()
              //      .map(Enseignement::getNom) // Assuming Enseignement has a 'getNom' method
                //    .collect(Collectors.joining(","));
            //preparedStatement.setString(4, planEnseignementsString);

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
                ", etudiant=" + etudiantId +
                ", bourse=" + bourseId +
                ", planEnseignements=" + planEnseignements +
                ", evaluationEnseignant1=" + evaluationEnseignant1 +
                ", evaluationEnseignant2=" + evaluationEnseignant2 +
                '}';
    }
}