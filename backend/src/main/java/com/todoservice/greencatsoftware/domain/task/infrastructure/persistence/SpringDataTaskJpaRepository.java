package com.todoservice.greencatsoftware.domain.task.infrastructure.persistence;

import com.todoservice.greencatsoftware.domain.task.domain.entity.Task;
import com.todoservice.greencatsoftware.domain.task.domain.port.TaskRepository;
import com.todoservice.greencatsoftware.domain.task.presentation.dto.TaskDetailResponse;
import com.todoservice.greencatsoftware.domain.task.presentation.dto.TaskSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpringDataTaskJpaRepository extends JpaRepository<Task, Long> {

    @Query("""
            SELECT new com.todoservice.greencatsoftware.domain.task.presentation.dto.TaskSummaryResponse
                (
                t.title,
                t.status,
                t.priority,
                t.schedule.dueDate,
                t.dayLabel,
                t.project.color.id
                )
            FROM Task t
            WHERE t.status <> com.todoservice.greencatsoftware.common.enums.Status.COMPLETED
            """)
    List<TaskSummaryResponse> summaryListTask();

    @Query("""
            SELECT new com.todoservice.greencatsoftware.domain.task.presentation.dto.TaskSummaryResponse
                (
                t.title,
                t.status,
                t.priority,
                t.schedule.dueDate,
                t.dayLabel,
                t.project.color.id
                )
            FROM Task t
            WHERE t.status <> com.todoservice.greencatsoftware.common.enums.Status.COMPLETED
                AND t.schedule.startDate <= :today
                AND (t.schedule.dueDate IS NULL OR t.schedule.dueDate >= :today)
            """)
    List<TaskSummaryResponse> todayListTask(@Param("today") LocalDate today);

    @Query("""
           SELECT new com.todoservice.greencatsoftware.domain.task.presentation.dto.TaskDetailResponse
               (
                p.color.id,
                t.project.id,
                t.priority,
                t.title,
                t.description,
                t.dayLabel,
                t.schedule,
                t.status
                )
           FROM Task t
           JOIN t.project p
           WHERE t.id = :id
           """)
    TaskDetailResponse getTaskDetailById(@Param("id") Long id);

    @Repository
    @RequiredArgsConstructor
    class TaskRepositoryImpl implements TaskRepository {
        private final SpringDataTaskJpaRepository jpa;

        @Override
        public List<Task> findAll() {return jpa.findAll();}

        @Override
        public List<TaskSummaryResponse> summaryListTask() {return jpa.summaryListTask();}

        @Override
        public List<TaskSummaryResponse> todayListTask(LocalDate today) {return jpa.todayListTask(today);}

        @Override
        public TaskDetailResponse getTaskDetailById(Long id) {return jpa.getTaskDetailById(id);}

        @Override
        public Task save(Task task) {return jpa.save(task);}

        @Override
        public void deleteById(Long id) {jpa.deleteById(id);}

        @Override
        public Optional<Task> findById(Long id) {return jpa.findById(id);}
    }
}
