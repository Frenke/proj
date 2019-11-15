package com.marco.unicorsi.repository;

import com.marco.unicorsi.model.Lezione;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepo extends JpaRepository<Lezione, Integer>{
}