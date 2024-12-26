package com.lopparg.ticketly.ticket;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.priority = :priority")
    List<Ticket> findTicketByPriority(Priority priority);

    @Query("SELECT t FROM Ticket t WHERE t.status = :status")
    List<Ticket> findTicketByStatus(Status status);
}
