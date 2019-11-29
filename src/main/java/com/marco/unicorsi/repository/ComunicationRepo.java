package com.marco.unicorsi.repository;

import com.marco.unicorsi.model.Comunicazione;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComunicationRepo extends JpaRepository<Comunicazione, Integer>{}