package com.marco.unicorsi.repository;

import java.util.List;

import com.marco.unicorsi.model.Insegnamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InsRepo extends JpaRepository<Insegnamento, String>{
    
    @Query(value = "select * from corso c join insegnamento i on c.codice = i.codice where c.anno_accademico = :anno", nativeQuery = true)
    public List<Insegnamento> getInsByAnno(String anno);
}