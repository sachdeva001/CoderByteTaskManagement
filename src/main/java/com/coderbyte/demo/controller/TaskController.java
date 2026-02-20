package com.coderbyte.demo.controller;

import com.coderbyte.demo.dto.TaskDTO;
import com.coderbyte.demo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
/**
 * REST controller exposing CRUD operations for tasks.
 * Supports filtering, pagination and basic validation for requests.
 */
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    /**
     * List tasks with optional filters and pagination.
     *
     * @param title filter by title (contains, case-insensitive)
     * @param isCompleted filter by completion status
     * @param dueBefore filter tasks due on or before this ISO datetime
     * @param dueAfter filter tasks due on or after this ISO datetime
     * @param page page index (0-based)
     * @param size page size
     * @param sort sort definition (e.g. "id,asc")
     * @return page of TaskDTO
     */
    public Page<TaskDTO> list(@RequestParam(required = false) String title,
                              @RequestParam(required = false) Boolean isCompleted,
                              @RequestParam(required = false) String dueBefore,
                              @RequestParam(required = false) String dueAfter,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id,asc") String sort) {
        String[] parts = sort.split(",");
        String prop = parts.length > 0 ? parts[0] : "id";
        String dirStr = parts.length > 1 ? parts[1] : "asc";
        Sort.Direction direction = Sort.Direction.fromString(dirStr);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        return service.list(title, isCompleted, dueBefore, dueAfter, pageable);
    }

    @GetMapping("/{id}")
    /**
     * Get a single task by id.
     *
     * @param id task id
     * @return TaskDTO for the given id
     */
    public TaskDTO get(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    /**
     * Create a new task.
     *
     * @param dto task payload (validated)
     * @return created TaskDTO with generated id
     */
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody TaskDTO dto) {
        TaskDTO created = service.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    /**
     * Update an existing task.
     *
     * @param id task id
     * @param dto updated task payload
     * @return updated TaskDTO
     */
    public TaskDTO update(@PathVariable Integer id, @Valid @RequestBody TaskDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    /**
     * Soft-delete a task by id.
     *
     * @param id task id
     * @return empty 204 response
     */
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle")
    /**
     * Toggle the completion state of a task.
     *
     * @param id task id
     * @return updated TaskDTO
     */
    public TaskDTO toggle(@PathVariable Integer id) {
        return service.toggleComplete(id);
    }
}
