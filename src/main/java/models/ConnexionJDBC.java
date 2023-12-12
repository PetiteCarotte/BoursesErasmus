package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionJDBC {

    // Configuration de la connexion � la base de donn�es
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc";
    private static final String UTILISATEUR = "root";
    private static final String MOT_DE_PASSE = "password";

    public static Connection obtenirConnexion() {
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

    public static void main(String[] args) {
        Connection connexion = obtenirConnexion();

        // Utilisez la connexion pour effectuer des op�rations sur la base de donn�es

        fermerConnexion(connexion);
    }
}