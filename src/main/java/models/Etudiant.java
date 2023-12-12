package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Etudiant {
    private Long id;

    private String nom;
    private String prenom;

    private int numeroEtudiant;

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

    public int getNumeroEtudiant() {
        return numeroEtudiant;
    }

    public void setNumeroEtudiant(int numeroEtudiant) {
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
            String insertQuery = "INSERT INTO Etudiant (id, nom, prenom, numeroEtudiant, noteMoyenne) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);

            // Set the parameters
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setInt(4, numeroEtudiant);
            preparedStatement.setDouble(5, noteMoyenne);

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
//Pour les étudiants qui demandent une bourse, on veut connaitre leur nom, prénom, numéro
//étudiant, note moyenne du dernier semestre validé.