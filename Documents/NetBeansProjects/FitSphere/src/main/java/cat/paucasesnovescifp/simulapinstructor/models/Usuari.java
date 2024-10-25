/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.paucasesnovescifp.simulapinstructor.models;

/**
 *
 * @author aleja
 */
public class Usuari {
    private int id;
    private String nom;
    private String email;
    private String passwordHash;
    private boolean instructor;

    public Usuari(){
    }

    public Usuari(int id, String nom, String email, String passwordHash, boolean instructor) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.passwordHash = passwordHash;
        this.instructor = instructor;
    }
    
    @Override
    public String toString() {
        return "Usuari{" + "id=" + id + ", nom=" + nom + ", email=" + email + ", passwordhash=" + passwordHash + ", instructor=" + instructor + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordhash) {
        this.passwordHash = passwordhash;
    }

    public boolean getInstructor() {
        return instructor;
    }

    public void setInstructor(boolean instructor) {
        this.instructor = instructor;
    }

}
