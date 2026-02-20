package com.coderbyte.demo.mapper;

import com.coderbyte.demo.dto.TaskDTO;
import com.coderbyte.demo.model.Task;
import org.joda.time.DateTime;

public class TaskMapper {

    /**
     * Utility class for converting between `Task` entities and `TaskDTO` objects.
     */

    public static TaskDTO toDto(Task task) {
        /**
         * Convert `Task` entity to `TaskDTO`.
         */
        if (task == null) return null;
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setIsCompleted(task.isCompleted());
        if (task.getDueDate() != null) dto.setDueDate(task.getDueDate().toString());
        return dto;
    }

    public static Task toEntity(TaskDTO dto) {
        /**
         * Convert `TaskDTO` to `Task` entity. Date parsing is performed if
         * `dueDate` is present.
         */
        if (dto == null) return null;
        Task t = new Task();
        t.setId(dto.getId());
        t.setTitle(dto.getTitle());
        t.setDescription(dto.getDescription());
        t.setCompleted(dto.getIsCompleted() != null ? dto.getIsCompleted() : false);
        if (dto.getDueDate() != null && !dto.getDueDate().isEmpty()) {
            t.setDueDate(DateTime.parse(dto.getDueDate()));
        }
        return t;
    }
}
