package models;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Etudiant {
    private Long id;

    private String nom;
    private String prenom;

    private double numeroEtudiant;

    private double noteMoyenne;

    private List<Bourse> candidatures = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public double getNumeroEtudiant() {
        return numeroEtudiant;
    }

    public void setNumeroEtudiant(double numeroEtudiant) {
        this.numeroEtudiant = numeroEtudiant;
    }

    public double getNoteMoyenne() {
        return noteMoyenne;
    }

    public void setNoteMoyenne(double noteMoyenne) {
        this.noteMoyenne = noteMoyenne;
    }

    public List<Bourse> getCandidatures() {
        return candidatures;
    }

    public void setCandidatures(List<Bourse> candidatures) {
        this.candidatures = candidatures;
    }

    public void insertIntoDatabase() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get the database connection
            connection = ConnexionJDBC.getConnexion();

            // Prepare the SQL statement
            String insertQuery = "INSERT INTO Etudiant ( nom, prenom, numeroEtudiant, noteMoyenne) VALUES ( ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);

            // Set the parameters
            //preparedStatement.setLong(1, id);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setDouble(3, numeroEtudiant);
            preparedStatement.setDouble(4, noteMoyenne);

            // Execute the update
            preparedStatement.executeUpdate();

            System.out.println("Etudiant inserted into the database successfully.");

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
}