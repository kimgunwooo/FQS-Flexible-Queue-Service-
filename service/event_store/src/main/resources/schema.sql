-- 테이블이 존재할 경우 삭제
DROP TABLE IF EXISTS event_store;
-- 테이블 생성
CREATE TABLE event_store
(
    id                          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(16)  NOT NULL,
    message_retention_period    INT          NOT NULL,
    max_message_size            INT          NOT NULL,
    expiration_time             INT          NOT NULL,
    message_order_guaranteed    BOOLEAN      NOT NULL,
    message_duplication_allowed BOOLEAN      NOT NULL,
    secret_key                  VARCHAR(255) NOT NULL,
    created_at                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);