package com.ticketbooking.ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.ticketbooking.redis.RedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

  private final TicketRepository ticketRepository;
  private final RedisService redisService;
  private final RedissonClient redissonClient;

  private static int count = 0;

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

  // Cache-Aside Pattern (no locking)
  @Override
  public TicketDTO getTicketWithCache(Long id) {
    TicketEntity ticket = getFromCache(id);
    if (ticket != null) {
      log.info("FROM CACHE | ticket - id: {} - ticket: {}", id, ticket);
      return TicketMapper.toDTO(ticket);
    }
    return TicketMapper.toDTO(loadTicketFromDbAndCache(id));
  }

  // Cache-Aside Pattern, guarded by a distributed lock on cache-miss to avoid
  // a stampede of concurrent DB reads for the same ticket.
  @Override
  public TicketDTO getTicketWithDistributedLockCache(Long id) {
    // 1. get from cache
    TicketEntity ticket = getFromCache(id);

    // cache hit
    if (ticket != null) {
      log.info("FROM CACHE | ticket - id: {} - ticket: {}", id, ticket);
      return TicketMapper.toDTO(ticket);
    }

    // cache miss
    return TicketMapper.toDTO(getTicketWithLock(id));
  }

  private TicketEntity getFromCache(Long id) {
    return redisService.getObject(genTicketKey(id), TicketEntity.class);
  }

  private TicketEntity getTicketWithLock(Long id) {
    RLock lock = redissonClient.getLock(genTicketLockKey(id));
    boolean acquired = false;
    try {
      acquired = lock.tryLock(1, 5, TimeUnit.SECONDS);

      // if failed to acquire lock, return null
      if (!acquired) {
        log.warn("Failed to acquire lock for ticket id: {}", id);
        return null;
      }

      // check cache again after acquiring lock
      TicketEntity ticket = getFromCache(id);

      // if cache hit, return it
      if (ticket != null) {
        log.info("FROM CACHE (after lock) | ticket - id: {} - ticket: {}", id, ticket);
        return ticket;
      }

      // cache miss: load from DB and cache it
      ticket = loadTicketFromDbAndCache(id);
      return ticket;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("Interrupted while acquiring ticket lock for id: " + id, e);
    } finally {
      lock.unlock();
    }
  }

  private TicketEntity loadTicketFromDbAndCache(Long id) {
    log.info("FROM DATABASE | Getting ticket with id: {}", id);
    count++;
    redisService.setObject("count", count);
    TicketEntity ticket = ticketRepository.findById(id).orElse(null);
    redisService.setObject(genTicketKey(id), ticket);
    return ticket;
  }

  @Override
  public List<TicketDTO> listTickets() {
    return ticketRepository.findAll().stream()
        .map(TicketMapper::toDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<TicketDTO> getAvailableTickets(Long matchId) {
    return ticketRepository.findAvailableTickets(TicketStatusEnum.AVAILABLE.toInt(), matchId).stream()
        .map(TicketMapper::toDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<TicketDTO> getTicketsByMatchId(Long matchId, TicketStatusEnum status) {
    Integer statusValue = status != null ? status.toInt() : null;
    return ticketRepository.findByMatchId(matchId, statusValue).stream()
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
    return TicketCacheKeys.genTicketKey(ticketId);
  }

  private String genTicketLockKey(Long ticketId) {
    return TicketCacheKeys.genTicketLockKey(ticketId);
  }
}
