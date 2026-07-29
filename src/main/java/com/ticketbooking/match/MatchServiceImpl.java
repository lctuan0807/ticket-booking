package com.ticketbooking.match;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

  private final MatchRepository matchRepository;

  @Override
  public MatchDTO createMatch(CreateMatchRequest request) {
    MatchEntity match = new MatchEntity();
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

    MatchEntity saved = matchRepository.save(match);
    log.info("Booking Service: Created match {} vs {} (id={})", saved.getHomeTeam(), saved.getAwayTeam(),
        saved.getId());

    return MatchMapper.toDTO(saved);
  }

  @Override
  public MatchDTO getMatch(Long matchId) {
    MatchEntity match = matchRepository.findById(matchId)
        .orElseThrow(() -> new MatchNotFoundException(matchId));
    return MatchMapper.toDTO(match);
  }

  @Override
  public void deleteMatch(Long matchId) {
    if (!matchRepository.existsById(matchId)) {
      throw new MatchNotFoundException(matchId);
    }
    matchRepository.deleteById(matchId);
  }
}
