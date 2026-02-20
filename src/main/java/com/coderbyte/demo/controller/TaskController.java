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
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
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
    public TaskDTO get(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody TaskDTO dto) {
        TaskDTO created = service.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public TaskDTO update(@PathVariable Integer id, @Valid @RequestBody TaskDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle")
    public TaskDTO toggle(@PathVariable Integer id) {
        return service.toggleComplete(id);
    }
}
