package org.costa.API_Clinica.exception;

public record ErrorResponse(
        String timeStamp,
        int status,
        String error,
        String message,
        String path
) {
}
