package com.coderbyte.demo.repository;

import com.coderbyte.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * JPA repository for Task entities. Extends `JpaSpecificationExecutor` to
 * support dynamic filtering used by the service layer.
 */
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

}
