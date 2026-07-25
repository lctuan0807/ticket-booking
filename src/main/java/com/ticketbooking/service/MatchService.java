package com.ticketbooking.service;

import com.ticketbooking.dto.CreateMatchRequest;
import com.ticketbooking.dto.MatchDTO;

public interface MatchService {
  MatchDTO createMatch(CreateMatchRequest request);

  MatchDTO getMatch(Long matchId);

  void deleteMatch(Long matchId);
}
