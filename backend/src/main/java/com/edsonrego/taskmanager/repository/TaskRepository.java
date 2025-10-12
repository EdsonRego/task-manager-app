package com.edsonrego.taskmanager.repository;

import com.edsonrego.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // ✅ Tarefas concluídas ou pendentes
    List<Task> findByCompleted(boolean completed);

    // ✅ Tarefas vencidas
    List<Task> findByDueDateBefore(LocalDate date);

    // ✅ Tarefas com vencimento após uma data específica
    List<Task> findByDueDateAfter(LocalDate date);

    // ✅ Tarefas com vencimento entre duas datas
    List<Task> findByDueDateBetween(LocalDate start, LocalDate end);

    // ✅ Buscar por título OU descrição contendo palavra-chave (case-insensitive)
    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Task> searchByKeyword(String keyword);


}
