CREATE TABLE IF NOT EXISTS t_link_0
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    domain          VARCHAR(255),
    short_uri       VARCHAR(255),
    full_short_url  VARCHAR(512),
    origin_url      VARCHAR(2048),
    click_num       INT DEFAULT 0,
    gid             VARCHAR(64),
    enable_status   INT DEFAULT 0,
    created_type    INT DEFAULT 0,
    valid_date_type INT DEFAULT 0,
    valid_date      DATETIME,
    favicon         VARCHAR(255),
    created_at      DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS t_link_1 LIKE t_link_0;
CREATE TABLE IF NOT EXISTS t_link_2 LIKE t_link_0;
CREATE TABLE IF NOT EXISTS t_link_3 LIKE t_link_0;