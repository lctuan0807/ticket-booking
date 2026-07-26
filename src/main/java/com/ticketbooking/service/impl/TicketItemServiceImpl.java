package com.ticketbooking.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ticketbooking.dto.CreateTicketItemRequest;
import com.ticketbooking.dto.TicketItemDTO;
import com.ticketbooking.entity.TicketItemEntity;
import com.ticketbooking.enums.TicketItemStatusEnum;
import com.ticketbooking.exception.TicketItemNotFoundException;
import com.ticketbooking.mapper.TicketItemMapper;
import com.ticketbooking.repository.TicketItemRepository;
import com.ticketbooking.service.TicketItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketItemServiceImpl implements TicketItemService {

  private final TicketItemRepository ticketItemRepository;

  @Override
  public TicketItemDTO createTicketItem(CreateTicketItemRequest request) {
    TicketItemEntity entity = new TicketItemEntity();
    entity.setTicketId(request.getTicketId());
    entity.setTicketCode(request.getTicketCode());
    entity.setSeatSection(request.getSeatSection());
    entity.setSeatRow(request.getSeatRow());
    entity.setSeatNumber(request.getSeatNumber());
    entity.setStatus(TicketItemStatusEnum.ISSUED.toInt());
    entity.setCreatedAt(LocalDateTime.now());
    entity.setUpdatedAt(LocalDateTime.now());

    TicketItemEntity saved = ticketItemRepository.save(entity);
    log.info("Created ticket item (id={})", saved.getId());
    return TicketItemMapper.toDTO(saved);
  }

  @Override
  public TicketItemDTO getTicketItem(Long id) {
    TicketItemEntity entity = ticketItemRepository.findById(id)
        .orElseThrow(() -> new TicketItemNotFoundException(id));
    return TicketItemMapper.toDTO(entity);
  }

  @Override
  public List<TicketItemDTO> listTicketItems() {
    return ticketItemRepository.findAll().stream()
        .map(TicketItemMapper::toDTO)
        .collect(Collectors.toList());
  }

  @Override
  public TicketItemDTO updateTicketItem(Long id, CreateTicketItemRequest request) {
    TicketItemEntity entity = ticketItemRepository.findById(id)
        .orElseThrow(() -> new TicketItemNotFoundException(id));
    entity.setTicketCode(request.getTicketCode());
    entity.setSeatSection(request.getSeatSection());
    entity.setSeatRow(request.getSeatRow());
    entity.setSeatNumber(request.getSeatNumber());
    entity.setUpdatedAt(LocalDateTime.now());
    return TicketItemMapper.toDTO(ticketItemRepository.save(entity));
  }

  @Override
  public void deleteTicketItem(Long id) {
    if (!ticketItemRepository.existsById(id)) {
      throw new TicketItemNotFoundException(id);
    }
    ticketItemRepository.deleteById(id);
  }
}
