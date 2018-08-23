package com.gym.application.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "table_users")
public class Table {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "muscolo",nullable = false)
    private String muscolo;

    @Column(name = "esercizio",nullable = false)
    private String esercizio;

    @Column(name = "serie",nullable = false)
    private String serie;

    @Column(name = "repetizioni",nullable = false)
    private String repetizioni;

    @Column(name = "recupero",nullable = false)
    private String recupero;

    @Column(name = "note",nullable = false)
    private String note;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMuscolo() {
        return muscolo;
    }

    public void setMuscolo(String muscolo) {
        this.muscolo = muscolo;
    }

    public String getEsercizio() {
        return esercizio;
    }

    public void setEsercizio(String esercizio) {
        this.esercizio = esercizio;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getRepetizioni() {
        return repetizioni;
    }

    public void setRepetizioni(String repetizioni) {
        this.repetizioni = repetizioni;
    }

    public String getRecupero() {
        return recupero;
    }

    public void setRecupero(String recupero) {
        this.recupero = recupero;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
