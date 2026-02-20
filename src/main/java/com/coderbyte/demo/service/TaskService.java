package com.coderbyte.demo.service;

import com.coderbyte.demo.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service contract that defines operations for managing tasks.
 */
public interface TaskService {

    /**
     * List tasks with optional filters and pagination.
     */
    Page<TaskDTO> list(String title, Boolean isCompleted, String dueBefore, String dueAfter, Pageable pageable);

    /**
     * Retrieve a task by id.
     */
    TaskDTO getById(Integer id);

    /**
     * Create a new task.
     */
    TaskDTO create(TaskDTO dto);

    /**
     * Update an existing task.
     */
    TaskDTO update(Integer id, TaskDTO dto);

    /**
     * Soft-delete a task by id (mark deleted flag).
     */
    void softDelete(Integer id);

    /**
     * Toggle completion state for a task.
     */
    TaskDTO toggleComplete(Integer id);

}
