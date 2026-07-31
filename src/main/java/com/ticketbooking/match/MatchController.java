package com.ticketbooking.match;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticketbooking.common.ResultMessage;
import com.ticketbooking.common.ResultUtil;
import com.ticketbooking.ticket.TicketDTO;
import com.ticketbooking.ticket.TicketService;
import com.ticketbooking.ticket.TicketStatusEnum;

import java.util.List;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/matches")
@Slf4j
@RequiredArgsConstructor
public class MatchController {

  private final MatchService matchService;
  private final TicketService ticketService;

  /**
   * Purpose: Create a new match.
   * Endpoint: POST /api/v1/matches
   * Params: request (body) - CreateMatchRequest with match details.
   * Return: 200 with the created MatchDTO; 500 on failure.
   */
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

  /**
   * Purpose: Fetch a single match by id.
   * Endpoint: GET /api/v1/matches/{matchId}
   * Params: matchId (path) - id of the match to fetch.
   * Return: 200 with the MatchDTO; 404 if not found; 500 on failure.
   */
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

  /**
   * Purpose: Fetch the tickets belonging to a match, optionally filtered by status.
   * Endpoint: GET /api/v1/matches/{matchId}/tickets
   * Params: matchId (path) - id of the match; status (query, optional) - TicketStatusEnum
   * (AVAILABLE, SOLD_OUT, CLOSED, CANCELLED) to filter by; omit to return tickets of any status.
   * Return: 200 with the list of TicketDTO for the match; 404 if the match does not exist; 500 on failure.
   */
  @GetMapping("/{matchId}/tickets")
  public ResponseEntity<ResultMessage<List<TicketDTO>>> getMatchTickets(@PathVariable Long matchId,
      @RequestParam(required = false) TicketStatusEnum status) {
    log.info("Getting tickets for match with id: {}, status: {}", matchId, status);
    try {
      matchService.getMatch(matchId);
      return ResponseEntity.ok(ResultUtil.data(ticketService.getTicketsByMatchId(matchId, status)));
    } catch (MatchNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultUtil.error(404, e.getMessage()));
    } catch (Exception e) {
      log.error("Error getting tickets for match", e);
      return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Failed to get tickets for match"));
    }
  }

  /**
   * Purpose: Delete a match by id.
   * Endpoint: DELETE /api/v1/matches/{matchId}
   * Params: matchId (path) - id of the match to delete.
   * Return: 200 on success; 404 if not found; 500 on failure.
   */
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
