package com.coderbyte.demo.service;

import com.coderbyte.demo.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    Page<TaskDTO> list(String title, Boolean isCompleted, String dueBefore, String dueAfter, Pageable pageable);

    TaskDTO getById(Integer id);

    TaskDTO create(TaskDTO dto);

    TaskDTO update(Integer id, TaskDTO dto);

    void softDelete(Integer id);

    TaskDTO toggleComplete(Integer id);

}
