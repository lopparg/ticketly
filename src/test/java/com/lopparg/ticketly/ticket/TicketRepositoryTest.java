package com.lopparg.ticketly.ticket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class TicketRepositoryTest {
    @Autowired
    TicketRepository ticketRepository;

    @AfterEach
    public void cleanUpEach() {
        ticketRepository.deleteAll();
    }

    @Test
    public void persist_ticket() {
        Ticket ticket = new Ticket("First ticket", "Test ticket", Priority.LOW);
        Long ticketId = ticketRepository.save(ticket).getId();
        assertTrue(ticketRepository.findById(ticketId).isPresent());
    }

    @Test
    public void find_ticket_by_id() {
        List<Ticket> tickets = Arrays.asList(
            new Ticket("Second ticket", "Test 2 ticket", Priority.LOW),
            new Ticket("Third ticket", "Test 3 ticket", Priority.LOW),
            new Ticket("Fourth ticket", "Test 4 ticket", Priority.LOW)
        );
        ticketRepository.saveAll(tickets);
        Ticket ticket = new Ticket("First ticket", "Test ticket", Priority.LOW);
        Long ticketId = ticketRepository.save(ticket).getId();
        Ticket savedTicket = ticketRepository.findById(ticketId).orElse(null);
        assertNotNull(savedTicket);
        assertEquals("First ticket", savedTicket.getTitle());
    }

    @Test
    public void filter_tickets_by_priority() {
        List<Ticket> tickets = Arrays.asList(
                new Ticket("First ticket", "Test 1 ticket", Priority.LOW),
                new Ticket("Second ticket", "Test 2 ticket", Priority.LOW),
                new Ticket("Third ticket", "Test 3 ticket", Priority.LOW),
                new Ticket("Fourth ticket", "Test 4 ticket", Priority.LOW),
                new Ticket("Fifth ticket", "Test 5 ticket", Priority.HIGH),
                new Ticket("Sixth ticket", "Test 6 ticket", Priority.HIGH),
                new Ticket("Seventh ticket", "Test 7 ticket", Priority.MEDIUM),
                new Ticket("Eight ticket", "Test 8 ticket", Priority.LOW),
                new Ticket("Ninth ticket", "Test 9 ticket", Priority.HIGH)
        );
        ticketRepository.saveAll(tickets);

        List<Ticket> lowPriorityTickets = ticketRepository.findTicketByPriority(Priority.LOW);
        List<Ticket> mediumPriorityTickets = ticketRepository.findTicketByPriority(Priority.MEDIUM);
        List<Ticket> highPriorityTickets = ticketRepository.findTicketByPriority(Priority.HIGH);

        assertEquals(5, lowPriorityTickets.size());
        assertEquals(1, mediumPriorityTickets.size());
        assertEquals(3, highPriorityTickets.size());
    }

    @Test
    public void filter_tickets_by_status() {
        List<Ticket> tickets = Arrays.asList(
                new Ticket("First ticket", "Test 1 ticket", Priority.LOW),
                new Ticket("Second ticket", "Test 2 ticket", Priority.LOW),
                new Ticket("Third ticket", "Test 3 ticket", Priority.LOW),
                new Ticket("Fourth ticket", "Test 4 ticket", Priority.LOW),
                new Ticket("Fifth ticket", "Test 5 ticket", Priority.HIGH),
                new Ticket("Sixth ticket", "Test 6 ticket", Priority.HIGH),
                new Ticket("Seventh ticket", "Test 7 ticket", Priority.MEDIUM),
                new Ticket("Eight ticket", "Test 8 ticket", Priority.LOW),
                new Ticket("Ninth ticket", "Test 9 ticket", Priority.HIGH)
        );
        Iterable<Ticket> ticketEntities = ticketRepository.saveAll(tickets);
        Long ticketId = ticketEntities.iterator().next().getId();

        Ticket someTicket = ticketRepository.findById(ticketId).orElse(null);
        someTicket.close();
        ticketRepository.save(someTicket);

        List<Ticket> closedTickets = ticketRepository.findTicketByStatus(Status.CLOSED);
        boolean found = closedTickets.stream()
                .anyMatch(ticket -> ticket.getId().equals(someTicket.getId()));
        assertTrue(found);
    }
}
