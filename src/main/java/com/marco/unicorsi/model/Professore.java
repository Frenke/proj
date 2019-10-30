package com.marco.unicorsi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Professore{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_prof")
    int id;

    @NotEmpty
    String nome;

    @NotEmpty
    String cognome;

    @NotEmpty
    String mail;

    String telefono;
    String studio;
    String web;
    String qualifica;

    @OneToOne()
    @JoinColumn(name = "id_user")
    User user;

}