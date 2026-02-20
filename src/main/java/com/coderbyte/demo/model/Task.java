package com.coderbyte.demo.model;

import jakarta.persistence.*;
import org.joda.time.DateTime;

@Entity
@Table(name = "tasks")
/**
 * JPA entity representing a task stored in the `tasks` table.
 * Contains fields for title, description, due date and soft-delete flag.
 */
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private boolean isCompleted = false;

    @Convert(converter = com.coderbyte.demo.converter.JodaDateTimeAttributeConverter.class)
    private DateTime dueDate;

    @Column(nullable = false)
    private boolean deleted = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public DateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(DateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
