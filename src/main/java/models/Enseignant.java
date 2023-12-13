package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Enseignant {

    private String nom;
    private String prenom;

    public Enseignant(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    // Getters et setters
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

    public void insertIntoDatabase() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get the database connection
            connection = ConnexionJDBC.getConnexion();

            // Prepare the SQL statement
            String insertQuery = "INSERT INTO Enseignant (nom, prenom) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);

            // Set the parameters
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);

            // Execute the update
            preparedStatement.executeUpdate();

            System.out.println("Enseignant inserted into the database successfully.");

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
        return "Enseignant{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}