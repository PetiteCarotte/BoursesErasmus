package models;

import java.util.List;

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