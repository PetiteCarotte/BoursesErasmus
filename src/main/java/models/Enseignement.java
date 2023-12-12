package models;

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