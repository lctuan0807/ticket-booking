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
