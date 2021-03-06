package com.marco.unicorsi.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"id_corso", "id_ins"}))
public class Corso{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_corso")
    int idCorso;

    @ManyToOne
    @JoinColumn(name = "id_ins")
    Insegnamento insegnamento;

    @NotNull
    String annoAccademico;

    @NotNull
    @Column(columnDefinition = "TEXT")
    String programma;

    @NotNull
    String modEsame;

    @ManyToMany(mappedBy = "corsi")
    Set<Professore> titolari;

    @OneToMany(mappedBy = "corso", orphanRemoval = true)
    @OrderBy("data DESC") //Con questa annotazione si ottengono le lezioni dalla più recente alla più lontana
    List<Lezione> lezioni;

    @OneToMany(mappedBy = "corso", orphanRemoval = true)
    @OrderBy("data_ora DESC")
    List<Comunicazione> comunicazioni;

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

    /**
     * @return the titolari
     */
    public Set<Professore> getTitolari() {
        return titolari;
    }
    
    /**
     * @param titolari the titolari to set
     */
    public void setTitolari(Set<Professore> titolari) {
        this.titolari = titolari;
    }

    /**
     * @return the lezioni
     */
    public List<Lezione> getLezioni() {
        return lezioni;
    }
    
    /**
     * @param lezioni the lezioni to set
     */
    public void setLezioni(List<Lezione> lezioni) {
        this.lezioni = lezioni;
    }

    /**
     * @return the comunicazioni
     */
    public List<Comunicazione> getComunicazioni() {
        return comunicazioni;
    }

    /**
     * @param comunicazioni the comunicazioni to set
     */
    public void setComunicazioni(List<Comunicazione> comunicazioni) {
        this.comunicazioni = comunicazioni;
    }

}