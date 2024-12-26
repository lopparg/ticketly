package com.lopparg.ticketly.ticket;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TicketTest {
    @Test
    public void expect_ticket_to_have_status_OPEN_when_created() {
        Ticket ticket = new Ticket("First ticket", "Test ticket", Priority.LOW);
        assertEquals(Status.OPEN, ticket.getStatus());
    }

    @Test
    public void expect_ticket_to_have_title() {
        Ticket ticket = new Ticket("Performance problems", "Slow system", Priority.LOW);
        assertEquals("Performance problems", ticket.getTitle());
    }

    @Test
    public void ticket_title_can_be_empty_string() {
        Ticket ticket = new Ticket("", "Slow performance", Priority.LOW);
        assertEquals("", ticket.getTitle());
    }

    @Test
    public void ticket_title_should_not_be_null() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(null, "System is slow", Priority.LOW));
    }

    @Test
    public void ticket_title_can_be_changed() {
        Ticket ticket = new Ticket("Bad Performance", "Slow performance", Priority.LOW);
        ticket.setTitle("Bad database performance");
        assertEquals("Bad database performance", ticket.getTitle());
    }

    @Test
    public void ticket_title_can_not_be_changed_to_null() {
        Ticket ticket = new Ticket("Bad Performance", "Slow performance", Priority.LOW);
        assertThrows(IllegalArgumentException.class, () -> ticket.setTitle(null));
    }

    @Test
    public void expect_ticket_to_have_description() {
        Ticket ticket = new Ticket("", "System is slow", Priority.LOW);
        assertEquals("System is slow", ticket.getDescription());
    }

    @Test
    public void ticket_description_can_be_changed() {
        Ticket ticket = new Ticket("Bad Performance", "Slow performance", Priority.LOW);
        ticket.setDescription("Bad database performance");
        assertEquals("Bad database performance", ticket.getDescription());
    }

    @Test
    public void ticket_description_can_be_empty_string() {
        Ticket ticket = new Ticket("Create a hello world", "", Priority.LOW);
        assertEquals("", ticket.getDescription());
    }

    @Test
    public void ticket_description_can_not_be_changed_to_null() {
        Ticket ticket = new Ticket("Bad Performance", "Slow performance", Priority.LOW);
        assertThrows(IllegalArgumentException.class, () -> ticket.setDescription(null));
    }

    @Test
    public void ticket_description_should_not_be_null() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket("Hello world", null, Priority.LOW));
    }

    @Test
    public void expect_ticket_to_have_priority() {
        Ticket ticket = new Ticket("", "System is slow", Priority.LOW);
        assertEquals(Priority.LOW, ticket.getPriority());
    }

    @Test
    public void priority_of_the_ticket_can_be_changed() {
        Ticket ticket = new Ticket("", "System is slow", Priority.LOW);
        ticket.setPriority(Priority.MEDIUM);
        assertEquals(Priority.MEDIUM, ticket.getPriority());
        ticket.setPriority(Priority.HIGH);
        assertEquals(Priority.HIGH, ticket.getPriority());
    }

    @Test
    public void ticket_priority_can_not_be_changed_to_null() {
        Ticket ticket = new Ticket("Bad Performance", "Slow performance", Priority.LOW);
        assertThrows(IllegalArgumentException.class, () -> ticket.setPriority(null));
    }

    @Test
    public void empty_priority_is_not_allowed() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket("Bad Performance", "System is slow", null));
    }

    @Test
    public void ticket_can_be_closed() {
        Ticket ticket = new Ticket("Bad Performance", "System is slow", Priority.LOW);
        ticket.close();
        assertEquals(Status.CLOSED, ticket.getStatus());
    }
}
