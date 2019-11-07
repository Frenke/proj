package com.marco.unicorsi.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;


@Entity(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    int idUser;

    @NotEmpty
    String username;

    @NotEmpty
    String password;

    @OneToOne
    @JoinColumn(name = "id_docente", referencedColumnName = "id_prof")
    Professore docente;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "role_user", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
    Set<Role> ruoli;
    
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the idUser
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the ruoli
     */
    public Set<Role> getRuoli() {
        return ruoli;
    }

    /**
     * @param ruoli the ruoli to set
     */
    public void setRuoli(Set<Role> ruoli) {
        this.ruoli = ruoli;
    }

    /**
     * @return the docente
     */
    public Professore getDocente() {
        return docente;
    }

    /**
     * @param docente the docente to set
     */
    public void setDocente(Professore docente) {
        this.docente = docente;
    }

}