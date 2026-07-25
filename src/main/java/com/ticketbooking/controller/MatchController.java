package com.ticketbooking.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketbooking.dto.CreateMatchRequest;
import com.ticketbooking.entity.Match;
import com.ticketbooking.enums.MatchStatusEnum;
import com.ticketbooking.repository.MatchRepository;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/matches")
@Slf4j
@RequiredArgsConstructor
public class MatchController {

  private final MatchRepository matchRepository;

  @PostMapping
  public ResponseEntity<Match> createMatch(@RequestBody @Valid CreateMatchRequest request) {
    Match match = new Match();
    match.setCompetition(request.getCompetition());
    match.setStage(request.getStage());
    match.setSeason(request.getSeason());
    match.setHomeTeam(request.getHomeTeam());
    match.setAwayTeam(request.getAwayTeam());
    match.setMatchDate(request.getMatchDate());
    match.setStadiumName(request.getStadiumName());
    match.setStatus(MatchStatusEnum.SCHEDULED.toInt());
    match.setCreatedAt(LocalDateTime.now());
    match.setUpdatedAt(LocalDateTime.now());

    Match saved = matchRepository.save(match);
    log.info("Created match {} vs {} (id={})", saved.getHomeTeam(), saved.getAwayTeam(), saved.getId());
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Match> getMatch(@PathVariable Long id) {
    return matchRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public List<Match> listMatches() {
    return matchRepository.findAll();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Match> updateMatch(@PathVariable Long id, @RequestBody @Valid CreateMatchRequest request) {
    return matchRepository.findById(id)
        .map(match -> {
          match.setCompetition(request.getCompetition());
          match.setStage(request.getStage());
          match.setSeason(request.getSeason());
          match.setHomeTeam(request.getHomeTeam());
          match.setAwayTeam(request.getAwayTeam());
          match.setMatchDate(request.getMatchDate());
          match.setStadiumName(request.getStadiumName());
          match.setUpdatedAt(LocalDateTime.now());
          return ResponseEntity.ok(matchRepository.save(match));
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
    if (!matchRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }
    matchRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
