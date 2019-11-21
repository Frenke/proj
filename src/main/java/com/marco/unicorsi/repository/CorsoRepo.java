package com.marco.unicorsi.repository;

import java.util.List;

import com.marco.unicorsi.model.Corso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CorsoRepo extends JpaRepository<Corso, Integer>{

    @Query(value = "select * from corso c join insegnamento i on c.codice = i.codice where c.codice = ?1 and anno_accademico = ?2", nativeQuery = true)
    public Corso findByInsegnamentoAndAnnoAccademico(String codice, String annoAccademico);

    public List<Corso> findByAnnoAccademico(String annoAccademico);

}