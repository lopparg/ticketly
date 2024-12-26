package com.lopparg.ticketly;

import com.lopparg.ticketly.ticket.Priority;
import com.lopparg.ticketly.ticket.Ticket;
import com.lopparg.ticketly.ticket.TicketRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketRepository ticketRepository;

    @BeforeEach
    public void setup() {
    }

    @AfterEach
    public void cleanUpEach() {
        ticketRepository.deleteAll();
    }

    @Test
    public void get_all_tickets() throws Exception {
        List<Ticket> tickets = Arrays.asList(
                new Ticket("Second ticket", "Test 2 ticket", Priority.LOW),
                new Ticket("Third ticket", "Test 3 ticket", Priority.LOW),
                new Ticket("Fourth ticket", "Test 4 ticket", Priority.LOW)
        );
        ticketRepository.saveAll(tickets);
        ResultActions result = mockMvc.perform(get("/api/tickets"));
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void get_ticket_by_id() throws Exception {
        Ticket ticket = new Ticket("First ticket", "Test ticket", Priority.LOW);
        Long ticketId = ticketRepository.save(ticket).getId();

        ResultActions result = mockMvc.perform(get("/api/tickets/{id}", ticketId));
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ticketId));

    }

    @Test
    public void filter_ticket_by_status() throws Exception {
        List<Ticket> tickets = Arrays.asList(
                new Ticket("Second ticket", "Test 2 ticket", Priority.LOW),
                new Ticket("Third ticket", "Test 3 ticket", Priority.LOW),
                new Ticket("Fourth ticket", "Test 4 ticket", Priority.LOW)
        );
        ticketRepository.saveAll(tickets);

        Ticket ticket = new Ticket("First ticket", "Test ticket", Priority.LOW);
        ticket.close();
        ticketRepository.save(ticket);

        ResultActions result = mockMvc.perform(get("/api/tickets?status=OPEN"));
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));

        ResultActions result1 = mockMvc.perform(get("/api/tickets?status=CLOSED"));
        result1.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void filter_ticket_by_priority() throws Exception {
        List<Ticket> tickets = Arrays.asList(
            new Ticket("Second ticket", "Test 2 ticket", Priority.LOW),
            new Ticket("Third ticket", "Test 3 ticket", Priority.LOW),
            new Ticket("Fourth ticket", "Test 4 ticket", Priority.LOW),
            new Ticket("Foo", "Test ticket", Priority.MEDIUM),
            new Ticket("Bar", "Test ticket", Priority.HIGH)
        );
        ticketRepository.saveAll(tickets);

        ResultActions lowPriorityTickets = mockMvc.perform(get("/api/tickets?priority=LOW"));
        lowPriorityTickets.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));

        ResultActions mediumPriorityTickets = mockMvc.perform(get("/api/tickets?priority=MEDIUM"));
        mediumPriorityTickets.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));

        ResultActions highPriorityTickets = mockMvc.perform(get("/api/tickets?priority=HIGH"));
        highPriorityTickets.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void close_ticket() throws Exception {
        List<Ticket> tickets = Arrays.asList(
            new Ticket("Second ticket", "Test 2 ticket", Priority.LOW),
            new Ticket("Third ticket", "Test 3 ticket", Priority.LOW),
            new Ticket("Fourth ticket", "Test 4 ticket", Priority.LOW)
        );
        ticketRepository.saveAll(tickets);
        Ticket ticket = new Ticket("First ticket", "Test ticket", Priority.LOW);
        Long ticketId = ticketRepository.save(ticket).getId();

        mockMvc.perform(post("/api/tickets/{id}/close", ticketId));

        ResultActions singleClosedTicket = mockMvc.perform(get("/api/tickets/{id}", ticketId));
        singleClosedTicket.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("CLOSED"));
    }

    @Test
    public void create_ticket() throws Exception {
        String ticket = """
                {
                    "title": "New ticket system",
                    "description": "New ticket system",
                    "priority": "HIGH"
                }
                """;

        ResultActions result = mockMvc.perform(post("/api/tickets")
                .content(ticket)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("New ticket system"))
                .andExpect(jsonPath("$.description").value("New ticket system"))
                .andExpect(jsonPath("$.status").value("OPEN"))
                .andExpect(jsonPath("$.priority").value("HIGH"));
    }

    @Test
    public void update_ticket() throws Exception {
        String ticketAsJson = """
                {
                    "title": "New title",
                    "description": "New description",
                    "priority": "HIGH"
                }
                """;

        Ticket ticket = new Ticket("First ticket", "Test ticket", Priority.LOW);
        Long ticketId = ticketRepository.save(ticket).getId();

        ResultActions result = mockMvc.perform(put("/api/tickets/{id}", ticketId)
                .content(ticketAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ticketId))
                .andExpect(jsonPath("$.title").value("New title"))
                .andExpect(jsonPath("$.description").value("New description"))
                .andExpect(jsonPath("$.status").value("OPEN"))
                .andExpect(jsonPath("$.priority").value("HIGH"));
    }
}
