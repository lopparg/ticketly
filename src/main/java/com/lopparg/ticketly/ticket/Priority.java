package com.lopparg.ticketly.ticket;

/**
 * Enum representing the priority levels for a ticket in the system.
 * <p>
 * A ticket can have one of the following priority levels:
 * <ul>
 *     <li>{@link Priority#MEDIUM} - The ticket has a medium priority and should be addressed after high-priority tickets.</li>
 *     <li>{@link Priority#HIGH} - The ticket has high priority and should be addressed as soon as possible.</li>
 *     <li>{@link Priority#LOW} - The ticket has low priority and can be addressed at a later time.</li>
 * </ul>
 * </p>
 * <p>
 * This enum is used to categorize tickets based on their urgency and importance, helping to prioritize tasks.
 * </p>
 */
public enum Priority {

    /**
     * The ticket has medium priority and should be addressed after high-priority tickets.
     */
    MEDIUM,

    /**
     * The ticket has high priority and should be addressed as soon as possible.
     */
    HIGH,

    /**
     * The ticket has low priority and can be addressed at a later time.
     */
    LOW
}