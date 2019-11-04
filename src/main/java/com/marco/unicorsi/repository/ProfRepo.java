package   com.marco.unicorsi.repository;

import com.marco.unicorsi.model.Professore;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfRepo extends JpaRepository<Professore, Integer>{
    
}