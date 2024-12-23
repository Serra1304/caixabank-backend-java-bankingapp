package com.hackathon.finservice.DTO;

import lombok.*;

/**
 * A simple DTO (Data Transfer Object) used to encapsulate a message response.
 * Typically used for returning success or error messages in API responses.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimpleMessageResponse {
    private String message;
}
