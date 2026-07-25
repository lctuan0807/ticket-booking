package com.ticketbooking.mapper;

import com.ticketbooking.dto.MatchDTO;
import com.ticketbooking.entity.MatchEntity;

public class MatchMapper {
  public static MatchDTO toDTO(MatchEntity matchEntity) {
    MatchDTO dto = new MatchDTO();
    dto.setId(matchEntity.getId());
    dto.setCompetition(matchEntity.getCompetition());
    dto.setStage(matchEntity.getStage());
    dto.setSeason(matchEntity.getSeason());
    dto.setHomeTeam(matchEntity.getHomeTeam());
    dto.setAwayTeam(matchEntity.getAwayTeam());
    dto.setMatchDate(matchEntity.getMatchDate());
    dto.setStadiumName(matchEntity.getStadiumName());
    return dto;
  }
}
