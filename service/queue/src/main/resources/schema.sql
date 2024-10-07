-- 테이블이 존재할 경우 삭제
DROP TABLE IF EXISTS queue;
DROP TABLE IF EXISTS queue_package;
DROP TABLE IF EXISTS queue_beans;

-- 테이블 생성
CREATE TABLE queue
(
    id                          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(16)  NOT NULL,
    message_retention_period    INT          NOT NULL,
    max_message_size            INT          NOT NULL,
    expiration_time             INT          NOT NULL,
    message_order_guaranteed    BOOLEAN      NOT NULL,
    message_duplication_allowed BOOLEAN      NOT NULL,
    secret_key                  VARCHAR(255) NOT NULL UNIQUE,
    created_at                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE queue_package
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    producer_id BIGINT NOT NULL,
    consumer_id BIGINT NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (producer_id) REFERENCES queue_beans(id),
    FOREIGN KEY (consumer_id) REFERENCES queue_beans(id)
);

CREATE TABLE queue_beans
(
    id                          BIGINT AUTO_INCREMENT PRIMARY KEY,
    bean_name                   VARCHAR(16) NOT NULL,
    message_order_guaranteed    BOOLEAN     NOT NULL,
    message_duplication_allowed BOOLEAN     NOT NULL,
    created_at                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);