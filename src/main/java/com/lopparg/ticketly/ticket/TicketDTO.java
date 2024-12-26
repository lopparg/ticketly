package com.lopparg.ticketly.ticket;

import io.swagger.v3.oas.annotations.media.Schema;


public class TicketDTO {

    @Schema(description = "The title of the ticket",
            example = "Init project",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "The description of the ticket",
            example = "Please initialize Springboot project",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "The title of the ticket", example = "HIGH",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Priority priority;

    public String getDescription() {
        return this.description;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public String getTitle() {
        return this.title;
    }
}
