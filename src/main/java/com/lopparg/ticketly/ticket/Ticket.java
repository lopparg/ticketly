package com.lopparg.ticketly.ticket;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.lang.NonNull;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;
    private Status status;

    public Ticket(String title, String description, Priority priority) {
        if (priority == null || title == null || description == null) {
            throw new IllegalArgumentException("Null not allowed");
        }

        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = Status.OPEN;
    }

    public Ticket() {
        this.title = "";
        this.description = "";
        this.priority = Priority.LOW;
        this.status = Status.OPEN;
    }

    public Status getStatus() {
        return status;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public void setTitle(@NonNull String title) {
        if(title == null) {
            throw new IllegalArgumentException("Null not allowed");
        }
        this.title = title;
    }

    public void setDescription(@NonNull String description) {
        if(description == null) {
            throw new IllegalArgumentException("Null not allowed");
        }
        this.description = description;
    }

    public void setPriority(@NonNull Priority priority) {
        if(priority == null) {
            throw new IllegalArgumentException("Null not allowed");
        }
        this.priority = priority;
    }

    public void close() {
        this.status = Status.CLOSED;
    }

    public Long getId() {
        return this.id;
    }
}
