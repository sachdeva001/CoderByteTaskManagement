package com.coderbyte.demo.service;

import com.coderbyte.demo.model.Task;
import com.coderbyte.demo.repository.TaskRepository;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskRepositoryIntegrationTest {

    @Autowired
    private TaskRepository repository;

    @Test
    void saveAndRetrieveTask() {
        Task t = new Task();
        t.setTitle("Integration Test Task");
        t.setDescription("Created by test");
        t.setDueDate(DateTime.now().plusDays(1));
        Task saved = repository.save(t);
        Assertions.assertNotNull(saved.getId());
        Task found = repository.findById(saved.getId()).orElse(null);
        Assertions.assertNotNull(found);
        Assertions.assertEquals("Integration Test Task", found.getTitle());
    }
}
