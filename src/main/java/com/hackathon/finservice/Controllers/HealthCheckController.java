package com.hackathon.finservice.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class responsible for providing a health check endpoint for the API.
 * This is a simple endpoint used to check if the API is running and accessible.
 */
@RestController
@RequestMapping("/health")
public class HealthCheckController {

    /**
     * Endpoint for checking the health status of the API.
     * This method returns a simple string message indicating that the API is working.
     *
     * @return a string message confirming that the API is functioning
     */
    @GetMapping
    public String healthCheck() {
        return "API is working";
    }
}

