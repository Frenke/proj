package com.marco.unicorsi.repository;

import com.marco.unicorsi.model.Insegnamento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InsRepo extends JpaRepository<Insegnamento, String>{
    
}