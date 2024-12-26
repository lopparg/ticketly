package com.lopparg.ticketly.ticket;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Tickets", description = "Operations related to tickets")
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Operation(summary = "Get all tickets")
    @GetMapping(produces = "application/json")
    public List<Ticket> getAllTickets(
            @RequestParam(value = "status", required = false) Status status,
            @RequestParam(value = "priority", required = false) Priority priority
    ) {
        if(status != null) {
            return ticketRepository.findTicketByStatus(status);
        }
        if(priority != null) {
            return ticketRepository.findTicketByPriority(priority);
        }
        return (List<Ticket>) ticketRepository.findAll();
    }

    @Operation(summary = "Create a ticket")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public Ticket createTicket(
            @RequestBody TicketDTO ticketDTO) {
        Ticket ticket = new Ticket(
                ticketDTO.getTitle(),
                ticketDTO.getDescription(),
                ticketDTO.getPriority()
        );
        return ticketRepository.save(ticket);
    }

    @Operation(summary = "Update ticket")
    @PutMapping(value = "/{ticketId}", consumes = "application/json", produces = "application/json")
    public Ticket updateTicket(
            @RequestBody TicketDTO ticketDTO,
            @PathVariable Long ticketId
    ) {
        return ticketRepository.findById(ticketId)
                .map(ticket -> {
                    ticket.setTitle(ticketDTO.getTitle());
                    ticket.setDescription(ticketDTO.getDescription());
                    ticket.setPriority(ticketDTO.getPriority());
                    return ticketRepository.save(ticket);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));
    }

    @Operation(summary = "Get on specific ticket")
    @GetMapping(value = "/{ticketId}", produces = "application/json")
    public Ticket getTicket(
            @Parameter(description = "ID of the ticket to retrieve", required = true)
            @PathVariable Long ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));
    }

    @Operation(summary = "Close ticket")
    @PostMapping(value = "/{ticketId}/close", produces = "application/json")
    public ResponseEntity<Ticket> closeTicket(
            @Parameter(description = "ID of the ticket to retrieve", required = true)
            @PathVariable Long ticketId) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if(ticket.isPresent()) {
            Ticket t = ticket.get();
            t.close();
            ticketRepository.save(t);
            return new ResponseEntity<>(t, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
