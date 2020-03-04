package com.marco.unicorsi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class News{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Data dell'avviso, impostata automaticamente alla data di creazione dell'avviso
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date data = new Date();

    @NotNull
    String oggetto;

    @NotNull
    @Column(columnDefinition = "TEXT")
    String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User autore;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return the oggetto
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * @param oggetto the oggetto to set
     */
    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return the autore
     */
    public User getAutore() {
        return autore;
    }

    /**
     * @param autore the autore to set
     */
    public void setAutore(User autore) {
        this.autore = autore;
    }

}