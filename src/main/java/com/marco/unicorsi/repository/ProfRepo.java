package com.marco.unicorsi.repository;

import java.util.List;

import com.marco.unicorsi.model.Professore;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfRepo extends JpaRepository<Professore, Integer>{
    public List<Professore> findByCognomeContains(String cognome);
}