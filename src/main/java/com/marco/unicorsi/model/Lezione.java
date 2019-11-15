package com.marco.unicorsi.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Lezione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String descrizione;

    @NotNull
    private Date data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_corso")
    private Corso corso;

    public Lezione(){}

    public Lezione(Corso corso){
        this.corso = corso;
    }

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
     * @return the corso
     */
    public Corso getCorso() {
        return corso;
    }

    /**
     * @param corso the corso to set
     */
    public void setCorso(Corso corso) {
        this.corso = corso;
    }

    /**
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param descrizione the descrizione to set
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}