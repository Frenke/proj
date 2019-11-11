package com.marco.unicorsi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Insegnamento{

    @Id
    String codice;

    @NotEmpty
    String nome;

    @NotNull
    int anno;

    @NotEmpty
    String semestre;

    @NotNull
    int crediti;

    @NotEmpty
    String settore;

    public Insegnamento(){}

    /**
     * @return the codice
     */
    public String getCodice() {
        return codice;
    }

    /**
     * @param codice the codice to set
     */
    public void setCodice(String codice) {
        this.codice = codice;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the anno
     */
    public int getAnno() {
        return anno;
    }

    /**
     * @param anno the anno to set
     */
    public void setAnno(int anno) {
        this.anno = anno;
    }

    /**
     * @return the crediti
     */
    public int getCrediti() {
        return crediti;
    }

    /**
     * @param crediti the crediti to set
     */
    public void setCrediti(int crediti) {
        this.crediti = crediti;
    }

    /**
     * @return the semestre
     */
    public String getSemestre() {
        return semestre;
    }

    /**
     * @param semestre the semestre to set
     */
    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    /**
     * @return the settore
     */
    public String getSettore() {
        return settore;
    }

    /**
     * @param settore the settore to set
     */
    public void setSettore(String settore) {
        this.settore = settore;
    }

}