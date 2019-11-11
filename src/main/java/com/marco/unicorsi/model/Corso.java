package com.marco.unicorsi.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"id_corso", "codice"}))
public class Corso{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_corso")
    int idCorso;

    @ManyToOne
    @JoinColumn(name = "codice")
    Insegnamento insegnamento;

    @NotNull
    String annoAccademico;

    @NotNull
    String programma;

    @NotNull
    String modEsame;

    @ManyToMany(mappedBy = "corsi")
    Set<Professore> titolari;

    public Corso(){}

    /**
     * @return the idCorso
     */
    public int getIdCorso() {
        return idCorso;
    }

    /**
     * @param idCorso the idCorso to set
     */
    public void setIdCorso(int idCorso) {
        this.idCorso = idCorso;
    }

    /**
     * @return the insegnamento
     */
    public Insegnamento getInsegnamento() {
        return insegnamento;
    }

    /**
     * @param insegnamento the insegnamento to set
     */
    public void setInsegnamento(Insegnamento insegnamento) {
        this.insegnamento = insegnamento;
    }

    /**
     * @return the annoAccademico
     */
    public String getAnnoAccademico() {
        return annoAccademico;
    }

    /**
     * @param annoAccademico the annoAccademico to set
     */
    public void setAnnoAccademico(String annoAccademico) {
        this.annoAccademico = annoAccademico;
    }

    /**
     * @return the programma
     */
    public String getProgramma() {
        return programma;
    }

    /**
     * @param programma the programma to set
     */
    public void setProgramma(String programma) {
        this.programma = programma;
    }

    /**
     * @return the modEsame
     */
    public String getModEsame() {
        return modEsame;
    }

    /**
     * @param modEsame the modEsame to set
     */
    public void setModEsame(String modEsame) {
        this.modEsame = modEsame;
    }


}