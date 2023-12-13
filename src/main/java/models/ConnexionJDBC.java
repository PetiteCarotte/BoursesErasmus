package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionJDBC {

    // Configuration de la connexion � la base de donn�es
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc";
    private static final String UTILISATEUR = "root";
    private static final String MOT_DE_PASSE = "password";

    public static void main(String[] args) {
        Connection connexion = getConnexion();

        // Utilisez la connexion pour effectuer des op�rations sur la base de donn�es

        try (Connection connection = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
             Statement statement = connection.createStatement()) {

            // Création de la table Bourse
            String createBourseTable = "CREATE TABLE Bourse (" +
                    "id BIGINT PRIMARY KEY," +
                    "destination VARCHAR(255)," +
                    "postesDisponibles INT," +
                    "responsableLocal VARCHAR(255)" +
                    ")";
            statement.executeUpdate(createBourseTable);

            // Création de la table Enseignant
            String createEnseignantTable = "CREATE TABLE Enseignant (" +
                    "nom VARCHAR(255)," +
                    "prenom VARCHAR(255)," +
                    "PRIMARY KEY (nom, prenom)" +
                    ")";
            statement.executeUpdate(createEnseignantTable);

            // Création de la table Enseignement
            String createEnseignementTable = "CREATE TABLE Enseignement (" +
                    "id BIGINT PRIMARY KEY," +
                    "nom VARCHAR(255)," +
                    "credits INT," +
                    "volumeHoraire INT" +
                    ")";
            statement.executeUpdate(createEnseignementTable);

            // Création de la table Etudiant
            String createEtudiantTable = "CREATE TABLE Etudiant (" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    "nom VARCHAR(255)," +
                    "prenom VARCHAR(255)," +
                    "numeroEtudiant INT," +
                    "noteMoyenne DOUBLE," +
                    "candidature_id BIGINT" +
                    ")";
            statement.executeUpdate(createEtudiantTable);

            // Création de la table Candidature
            String createCandidatureTable = "CREATE TABLE Candidature (" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    "etudiant_id BIGINT," +
                    "bourse_id BIGINT," +
                    "evaluationEnseignant1 INT," +
                    "evaluationEnseignant2 INT" +
                    ")";
            statement.executeUpdate(createCandidatureTable);

            System.out.println("Tables créées avec succès.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
             Statement statement = connection.createStatement()) {

            // Ajout de la clé étrangère pour la table Etudiant
            String addForeignKeyEtudiant = "ALTER TABLE Etudiant " +
                    "ADD FOREIGN KEY (candidature_id) REFERENCES Candidature(id)";
            statement.executeUpdate(addForeignKeyEtudiant);

            // Ajout des clés étrangères pour la table Candidature
            String addForeignKeyCandidatureEtudiant = "ALTER TABLE Candidature " +
                    "ADD FOREIGN KEY (etudiant_id) REFERENCES Etudiant(id)";
            statement.executeUpdate(addForeignKeyCandidatureEtudiant);

            String addForeignKeyCandidatureBourse = "ALTER TABLE Candidature " +
                    "ADD FOREIGN KEY (bourse_id) REFERENCES Bourse(id)";
            statement.executeUpdate(addForeignKeyCandidatureBourse);

            System.out.println("Contraintes de clé étrangère ajoutées avec succès.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        fermerConnexion(connexion);
    }

    public static Connection getConnexion() {
        Connection connexion = null;

        try {
            // Chargement du pilote JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // �tablissement de la connexion
            connexion = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);

            if (connexion != null) {
                System.out.println("Connexion etablie avec succes !");
            } else {
                System.out.println("Echec de la connexion.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connexion;
    }

    public static void fermerConnexion(Connection connexion) {
        if (connexion != null) {
            try {
                connexion.close();
                System.out.println("Connexion fermee avec succes !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}