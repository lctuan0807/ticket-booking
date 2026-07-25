package com.ticketbooking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketbooking.dto.CreateTicketRequest;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/tickets")
@Slf4j
public class TicketController {

  @PostMapping
  public void createTicket(@RequestBody @Valid CreateTicketRequest request) {
    log.info("Creating ticket: {} vs {} ({}) at {}", request.getHomeTeam(), request.getAwayTeam(),
        request.getStage(), request.getVenue());
  }

  @GetMapping("/{id}")
  public void getTicket(@PathVariable Long id) {
    log.info("Getting ticket with id: {}", id);
  }
}
