package com.marco.unicorsi.repository;

import com.marco.unicorsi.model.News;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepo extends JpaRepository<News, Integer>{}