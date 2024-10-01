/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EjemploDTO.dto;

import java.util.Date;

/**
 *
 * @author aleja
 */
public class Client {
    private String nom;
    private String cognom;
    private Date date;
    private String illa;

    public Client(String nom, String cognom, Date date, String illa) {
        this.nom = nom;
        this.cognom = cognom;
        this.date = date;
        this.illa = illa;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIlla() {
        return illa;
    }

    public void setIlla(String illa) {
        this.illa = illa;
    }
    
    public String[] toArray(String); {
    

        String[] s = new String[];
        s[0] = DELOCS;
        return
                }
}
