package com.ticketbooking.match;

public interface MatchService {
  MatchDTO createMatch(CreateMatchRequest request);

  MatchDTO getMatch(Long matchId);

  void deleteMatch(Long matchId);
}
