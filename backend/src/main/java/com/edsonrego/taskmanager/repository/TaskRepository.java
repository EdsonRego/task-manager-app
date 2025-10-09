package com.edsonrego.taskmanager.repository;

import com.edsonrego.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Buscar todas as tasks concluídas
    List<Task> findByCompleted(boolean completed);

    // Buscar todas as tasks com data de vencimento anterior a uma data específica
    List<Task> findByDueDateBefore(LocalDate date);

    // Buscar todas as tasks com data de vencimento após uma data específica
    List<Task> findByDueDateAfter(LocalDate date);

    // Buscar todas as tasks por título contendo palavra-chave (case insensitive)
    List<Task> findByTitleContainingIgnoreCase(String keyword);
}
