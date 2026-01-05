package org.gstroke.response.dto;

public record CustomerResponse(
        Long id,
        String name,
        String documentNumber,
        String email,
        String status
) {}