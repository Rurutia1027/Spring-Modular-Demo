-- Create t_link_route_0 ~ t_link_route_3 if needed
CREATE TABLE IF NOT EXISTS t_link_route_0
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_short_url VARCHAR(512),
    target_table   VARCHAR(64),
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS t_link_route_1 LIKE t_link_route_0;
CREATE TABLE IF NOT EXISTS t_link_route_2 LIKE t_link_route_0;
CREATE TABLE IF NOT EXISTS t_link_route_3 LIKE t_link_route_0;