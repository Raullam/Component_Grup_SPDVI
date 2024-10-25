/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.paucasesnovescifp.simulapinstructor.models;

public class Review {

    // Atributos
    private int id;
    private int idIntent;
    private int idReviewer;
    private int valoracio;
    private String comentari;

    // Constructor vacío
    public Review() {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdIntent() {
        return idIntent;
    }

    public void setIdIntent(int idIntent) {
        this.idIntent = idIntent;
    }

    public int getIdReviewer() {
        return idReviewer;
    }

    public void setIdReviewer(int idReviewer) {
        this.idReviewer = idReviewer;
    }

    public int getValoracio() {
        return valoracio;
    }

    public void setValoracio(int valoracio) {
        this.valoracio = valoracio;
    }

    public String getComentari() {
        return comentari;
    }

    public void setComentari(String comentari) {
        this.comentari = comentari;
    }

    // Método toString
    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", idIntent=" + idIntent +
                ", idReviewer=" + idReviewer +
                ", valoracio=" + valoracio +
                ", comentari='" + comentari + '\'' +
                '}';
    }
}


