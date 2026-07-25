package com.ticketbooking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketbooking.dto.CreateMatchRequest;
import com.ticketbooking.dto.MatchDTO;
import com.ticketbooking.exception.MatchNotFoundException;
import com.ticketbooking.service.MatchService;
import com.ticketbooking.util.ResultMessage;
import com.ticketbooking.util.ResultUtil;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/matches")
@Slf4j
@RequiredArgsConstructor
public class MatchController {

  private final MatchService matchService;

  @PostMapping
  public ResponseEntity<ResultMessage<MatchDTO>> createMatch(@RequestBody @Valid CreateMatchRequest request) {
    try {
      MatchDTO dto = matchService.createMatch(request);
      return ResponseEntity.ok(ResultUtil.data(dto));
    } catch (Exception e) {
      log.error("Error creating match", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to create match"));
    }
  }

  @GetMapping("/{matchId}")
  public ResponseEntity<ResultMessage<MatchDTO>> getMatch(@PathVariable Long matchId) {
    log.info("Getting match with id: {}", matchId);
    try {
      MatchDTO dto = matchService.getMatch(matchId);
      return ResponseEntity.ok(ResultUtil.data(dto));
    } catch (MatchNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultUtil.error(404, e.getMessage()));
    } catch (Exception e) {
      log.error("Error getting match", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to get match"));
    }
  }

  @DeleteMapping("/{matchId}")
  public ResponseEntity<ResultMessage<Void>> deleteMatch(@PathVariable Long matchId) {
    log.info("Deleting match with id: {}", matchId);
    try {
      matchService.deleteMatch(matchId);
      return ResponseEntity.ok(ResultUtil.success());
    } catch (MatchNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultUtil.error(404, e.getMessage()));
    } catch (Exception e) {
      log.error("Error deleting match", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to delete match"));
    }
  }
}
