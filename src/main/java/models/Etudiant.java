package models;

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
}
//Pour les étudiants qui demandent une bourse, on veut connaitre leur nom, prénom, numéro
//étudiant, note moyenne du dernier semestre validé.