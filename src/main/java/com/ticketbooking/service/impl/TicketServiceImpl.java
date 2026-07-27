package com.ticketbooking.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ticketbooking.dto.CreateTicketRequest;
import com.ticketbooking.dto.TicketDTO;
import com.ticketbooking.entity.TicketEntity;
import com.ticketbooking.enums.TicketStatusEnum;
import com.ticketbooking.exception.TicketNotFoundException;
import com.ticketbooking.mapper.TicketMapper;
import com.ticketbooking.repository.TicketRepository;
import com.ticketbooking.service.RedisService;
import com.ticketbooking.service.TicketService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

  private final TicketRepository ticketRepository;
  private final RedisService redisService;

  @Override
  public TicketDTO createTicket(CreateTicketRequest request) {
    TicketEntity entity = new TicketEntity();
    entity.setMatchId(request.getMatchId());
    entity.setSeatCategory(request.getSeatCategory());
    entity.setPrice(request.getPrice());
    entity.setDescription(request.getDescription());
    entity.setTotalQuantity(request.getTotalQuantity());
    entity.setSoldQuantity(0);
    entity.setSaleStartAt(request.getSaleStartAt());
    entity.setSaleEndAt(request.getSaleEndAt());
    entity.setStatus(TicketStatusEnum.AVAILABLE.toInt());
    entity.setCreatedAt(LocalDateTime.now());
    entity.setUpdatedAt(LocalDateTime.now());

    TicketEntity saved = ticketRepository.save(entity);
    log.info("Created ticket (id={})", saved.getId());
    return TicketMapper.toDTO(saved);
  }

  @Override
  public TicketDTO getTicket(Long id) {
    TicketEntity entity = ticketRepository.findById(id)
        .orElseThrow(() -> new TicketNotFoundException(id));
    return TicketMapper.toDTO(entity);
  }

  // Cache-Aside Pattern
  // @Override
  // public TicketDTO getTicket(Long id) {
  // // get ticket from redis cache
  // TicketEntity ticket = redisService.getObject(genTicketKey(id),
  // TicketEntity.class);

  // // cache hit
  // if (ticket != null) {
  // log.info("FROM CACHE | ticket - id: {} - ticket: {}", id, ticket);
  // return TicketMapper.toDTO(ticket);
  // }

  // // cache miss
  // log.info("FROM DATABASE | Getting ticket with id: {}", id);
  // ticket = ticketRepository.findById(id)
  // .orElseThrow(() -> new TicketNotFoundException(id));
  // redisService.setObject(genTicketKey(id), ticket);
  // return TicketMapper.toDTO(ticket);
  // }

  @Override
  public List<TicketDTO> listTickets() {
    return ticketRepository.findAll().stream()
        .map(TicketMapper::toDTO)
        .collect(Collectors.toList());
  }

  @Override
  public TicketDTO updateTicket(Long id, CreateTicketRequest request) {
    TicketEntity entity = ticketRepository.findById(id)
        .orElseThrow(() -> new TicketNotFoundException(id));
    entity.setMatchId(request.getMatchId());
    entity.setSeatCategory(request.getSeatCategory());
    entity.setPrice(request.getPrice());
    entity.setDescription(request.getDescription());
    entity.setTotalQuantity(request.getTotalQuantity());
    entity.setSaleStartAt(request.getSaleStartAt());
    entity.setSaleEndAt(request.getSaleEndAt());
    entity.setUpdatedAt(LocalDateTime.now());
    TicketDTO dto = TicketMapper.toDTO(ticketRepository.save(entity));
    return dto;
  }

  @Override
  public void deleteTicket(Long id) {
    if (!ticketRepository.existsById(id)) {
      throw new TicketNotFoundException(id);
    }
    ticketRepository.deleteById(id);
  }

  private String genTicketKey(Long ticketId) {
    return "MATCH:TICKET:" + ticketId;
  }
}
