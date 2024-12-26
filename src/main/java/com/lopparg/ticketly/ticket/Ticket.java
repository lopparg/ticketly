package com.lopparg.ticketly.ticket;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.lang.NonNull;

/**
 * Represents a ticket in the ticketing system.
 * <p>
 * The {@link Ticket} class is used to store information about a ticket, including its title, description,
 * priority, and status. It also provides methods to manage the ticket's properties such as setting the title,
 * description, priority, and status.
 * </p>
 * <p>
 * The {@link Ticket} entity maintains the state of the ticket, which can be
 * either {@link Status#OPEN} or {@link Status#CLOSED}.
 * </p>
 */
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

    /**
     * Creates a new ticket with the specified title, description, and priority.
     * <p>
     * The status of the ticket is set to {@link Status#OPEN} by default.
     * </p>
     *
     * @param title the title of the ticket (cannot be null)
     * @param description the description of the ticket (cannot be null)
     * @param priority the priority of the ticket (cannot be null)
     * @throws IllegalArgumentException if any of the parameters are null
     */
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

    /**
     * Closes the ticket by setting its status to {@link Status#CLOSED}.
     */
    public void close() {
        this.status = Status.CLOSED;
    }

    public Long getId() {
        return this.id;
    }
}
