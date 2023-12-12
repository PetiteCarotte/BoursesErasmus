package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Bourse {

        private long id;
        private String destination;
        private int postesDisponibles;
        private String responsableLocal;

    // Constructeur
    public Bourse(String destination, int postesDisponibles, String responsableLocal) {
        this.destination = destination;
        this.postesDisponibles = postesDisponibles;
        this.responsableLocal = responsableLocal;
    }
    public Bourse(long id) {
        this.id = id;
    }

    // Getters et setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPostesDisponibles() {
        return postesDisponibles;
    }

    public void setPostesDisponibles(int postesDisponibles) {
        this.postesDisponibles = postesDisponibles;
    }

    public String getResponsableLocal() {
        return responsableLocal;
    }

    public void setResponsableLocal(String responsableLocal) {
        this.responsableLocal = responsableLocal;
    }

    public void insertIntoDatabase() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get the database connection
            connection = ConnexionJDBC.obtenirConnexion();

            // Prepare the SQL statement
            String insertQuery = "INSERT INTO Bourse (id, destination, postesDisponibles, responsableLocal) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);

            // Set the parameters
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, destination);
            preparedStatement.setInt(3, postesDisponibles);
            preparedStatement.setString(4, responsableLocal);

            // Execute the update
            preparedStatement.executeUpdate();

            System.out.println("Bourse inserted into the database successfully.");

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
        return "Bourse{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", postesDisponibles=" + postesDisponibles +
                ", responsableLocal='" + responsableLocal + '\'' +
                '}';
    }
}