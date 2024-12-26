package com.lopparg.ticketly.ticket;

/**
 * Enum representing the possible status values for a ticket in the system.
 * <p>
 * A ticket can be in one of the following statuses:
 * <ul>
 *     <li>{@link Status#OPEN} - The ticket is open and active, awaiting resolution.</li>
 *     <li>{@link Status#CLOSED} - The ticket has been resolved and is now closed.</li>
 * </ul>
 * </p>
 * <p>
 * This enum helps in tracking the state of tickets within the system, making it easier to filter and manage tickets based on their status.
 * </p>
 */
public enum Status {
    /**
     * The ticket is open and still requires attention.
     */
    CLOSED,

    /**
     * The ticket has been resolved and is now closed.
     */
    OPEN
}
