package models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Enseignement {

    private long id;
    private String nom;
    private int credits;
    private int volumeHoraire;

    public Enseignement(long id, String nom, int credits, int volumeHoraire) {
        this.id = id;
        this.nom = nom;
        this.credits = credits;
        this.volumeHoraire = volumeHoraire;
    }

    // Getters et setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getVolumeHoraire() {
        return volumeHoraire;
    }

    public void setVolumeHoraire(int volumeHoraire) {
        this.volumeHoraire = volumeHoraire;
    }

    public void insertIntoDatabase() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get the database connection
            connection = ConnexionJDBC.getConnexion();

            // Prepare the SQL statement
            String insertQuery = "INSERT INTO Enseignement (nom, credits, volumeHoraire) VALUES ( ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);

            // Set the parameters
            //preparedStatement.setLong(1, id);
            preparedStatement.setString(1, nom);
            preparedStatement.setInt(2, credits);
            preparedStatement.setInt(3, volumeHoraire);

            // Execute the update
            preparedStatement.executeUpdate();

            System.out.println("Enseignement inserted into the database successfully.");

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
        return "Enseignement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", credits=" + credits +
                ", volumeHoraire=" + volumeHoraire +
                '}';
    }
}