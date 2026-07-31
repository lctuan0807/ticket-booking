-- 1. Create match table
CREATE TABLE IF NOT EXISTS ticket_booking.matches (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  competition   VARCHAR(150)  NOT NULL COMMENT 'Match competition name',
  stage         VARCHAR(50)   NOT NULL COMMENT 'Match stage',
  season        VARCHAR(20)   NOT NULL COMMENT 'Match season',
  home_team     VARCHAR(100)  NOT NULL COMMENT 'Home team name',
  away_team     VARCHAR(100)  NOT NULL COMMENT 'Away team name',
  match_date    DATETIME      NOT NULL COMMENT 'Match date',
  stadium_name  VARCHAR(100)  NOT NULL COMMENT 'Stadium name',
  status        INT           NOT NULL DEFAULT 0 COMMENT 'Match status: 0=SCHEDULED, 1=ONGOING, 2=FINISHED, 3=POSTPONED, 4=CANCELLED',
  created_at    DATETIME      NOT NULL COMMENT 'Created at',
  updated_at    DATETIME      NOT NULL COMMENT 'Updated at'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Insert sample match
INSERT INTO ticket_booking.matches
  (competition, stage, season, home_team, away_team, match_date, stadium_name, status, created_at, updated_at)
VALUES
  ('Premier League', '4th Matchday', '2026', 'Man United', 'Man City', '2026-09-13 22:30:00', 'Old Trafford', 0, NOW(), NOW());

-- 3. Create ticket table
CREATE TABLE IF NOT EXISTS ticket_booking.tickets (
  id              BIGINT AUTO_INCREMENT PRIMARY KEY,
  match_id        BIGINT         NOT NULL COMMENT 'Match id this ticket batch belongs to',
  seat_category   VARCHAR(50)    NOT NULL COMMENT 'Seat category',
  price           DECIMAL(10,2)  NOT NULL COMMENT 'Ticket price',
  description     VARCHAR(500)   COMMENT 'Ticket description',
  total_quantity  INT            NOT NULL COMMENT 'Total number of tickets in this batch',
  sold_quantity   INT            NOT NULL DEFAULT 0 COMMENT 'Number of tickets sold so far',
  sale_start_at   DATETIME       NOT NULL COMMENT 'Sale start time',
  sale_end_at     DATETIME       NOT NULL COMMENT 'Sale end time',
  status          INT            NOT NULL DEFAULT 0 COMMENT 'Ticket status: 0=AVAILABLE, 1=SOLD_OUT, 2=CLOSED, 3=CANCELLED',
  created_at      DATETIME       NOT NULL COMMENT 'Created at',
  updated_at      DATETIME       NOT NULL COMMENT 'Updated at'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Insert sample ticket
INSERT INTO ticket_booking.tickets
  (match_id, seat_category, price, description, total_quantity, sold_quantity, sale_start_at, sale_end_at, status, created_at, updated_at)
VALUES
  (1, 'SAFS1', 150.00, 'Sir Alex Ferguson Stand N40-N49', 100, 0, '2026-08-01 09:00:00', '2026-09-13 20:00:00', 0, NOW(), NOW());

-- 5. Create ticket_item table
CREATE TABLE IF NOT EXISTS ticket_booking.ticket_items (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  ticket_id     BIGINT       NOT NULL COMMENT 'Ticket batch id this item belongs to',
  ticket_code   VARCHAR(50)  NOT NULL UNIQUE COMMENT 'Unique ticket code for this seat instance',
  seat_section  VARCHAR(50)  NOT NULL COMMENT 'Seat section',
  seat_row      VARCHAR(10)  NOT NULL COMMENT 'Seat row',
  seat_number   VARCHAR(10)  NOT NULL COMMENT 'Seat number',
  status        INT          NOT NULL DEFAULT 0 COMMENT 'Ticket item status: 0=ISSUED, 1=RESERVED, 2=SOLD, 3=CANCELLED',
  created_at    DATETIME     NOT NULL COMMENT 'Created at',
  updated_at    DATETIME     NOT NULL COMMENT 'Updated at'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. Insert sample ticket item
INSERT INTO ticket_booking.ticket_items
  (ticket_id, ticket_code, seat_section, seat_row, seat_number, status, created_at, updated_at)
VALUES
  (1, 'MU-MC-SAFS1-001', 'N40', 'N', '40', 0, NOW(), NOW());

-- 7. Create order table
CREATE TABLE IF NOT EXISTS ticket_booking.orders (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id       BIGINT         NOT NULL COMMENT 'Id of the user placing the order',
  ticket_id     BIGINT         NOT NULL COMMENT 'Ticket batch id being ordered',
  quantity      INT            NOT NULL COMMENT 'Number of tickets ordered',
  unit_price    DECIMAL(10,2)  NOT NULL COMMENT 'Ticket unit price at the time of order',
  total_amount  DECIMAL(10,2)  NOT NULL COMMENT 'Total order amount (unitPrice * quantity)',
  status        INT            NOT NULL DEFAULT 0 COMMENT 'Order status: 0=CONFIRMED, 1=CANCELLED, 2=FAILED',
  created_at    DATETIME       NOT NULL COMMENT 'Created at',
  updated_at    DATETIME       NOT NULL COMMENT 'Updated at'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
