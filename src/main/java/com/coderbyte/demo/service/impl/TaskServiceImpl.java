package com.coderbyte.demo.service.impl;

import com.coderbyte.demo.dto.TaskDTO;
import com.coderbyte.demo.exception.NotFoundException;
import com.coderbyte.demo.mapper.TaskMapper;
import com.coderbyte.demo.model.Task;
import com.coderbyte.demo.repository.TaskRepository;
import com.coderbyte.demo.service.TaskService;
import java.util.Objects;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<TaskDTO> list(String title, Boolean isCompleted, String dueBefore, String dueAfter, Pageable pageable) {
        Specification<Task> spec = (root, query, cb) -> cb.equal(root.get("deleted"), false);

        if (title != null && !title.isBlank()) {
            String lowered = title.toLowerCase();
            spec = spec.and((root, q, cb) -> cb.like(cb.lower(root.get("title")), "%" + lowered + "%"));
        }
        if (isCompleted != null) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("isCompleted"), isCompleted));
        }
        if (dueBefore != null && !dueBefore.isBlank()) {
            DateTime dt = DateTime.parse(dueBefore);
            spec = spec.and((root, q, cb) -> cb.lessThanOrEqualTo(root.get("dueDate"), dt));
        }
        if (dueAfter != null && !dueAfter.isBlank()) {
            DateTime dt = DateTime.parse(dueAfter);
            spec = spec.and((root, q, cb) -> cb.greaterThanOrEqualTo(root.get("dueDate"), dt));
        }

        return repository.findAll(spec, pageable).map(TaskMapper::toDto);
    }

    @Override
    public TaskDTO getById(Integer id) {
        Task t = repository.findById(id).filter(task -> !task.isDeleted()).orElseThrow(() -> new NotFoundException("Task not found"));
        return TaskMapper.toDto(t);
    }

    @Override
    public TaskDTO create(TaskDTO dto) {
        Task t = TaskMapper.toEntity(dto);
        t.setDeleted(false);
        Task saved = repository.save(t);
        return TaskMapper.toDto(saved);
    }

    @Override
    public TaskDTO update(Integer id, TaskDTO dto) {
        Task existing = repository.findById(id).filter(task -> !task.isDeleted()).orElseThrow(() -> new NotFoundException("Task not found"));
        if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setCompleted(dto.getIsCompleted() != null ? dto.getIsCompleted() : existing.isCompleted());
        if (dto.getDueDate() != null && !dto.getDueDate().isEmpty()) existing.setDueDate(DateTime.parse(dto.getDueDate()));
        Task saved = repository.save(existing);
        return TaskMapper.toDto(saved);
    }

    @Override
    public void softDelete(Integer id) {
        Task existing = repository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        existing.setDeleted(true);
        repository.save(existing);
    }

    @Override
    public TaskDTO toggleComplete(Integer id) {
        Task existing = repository.findById(id).filter(task -> !task.isDeleted()).orElseThrow(() -> new NotFoundException("Task not found"));
        existing.setCompleted(!existing.isCompleted());
        return TaskMapper.toDto(repository.save(existing));
    }
}
