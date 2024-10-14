-- queue_bean 테이블에 데이터 삽입
INSERT INTO queue_bean (name, message_order_guaranteed, message_duplication_allowed)
VALUES ('BeanA', TRUE, TRUE),
       ('BeanB', FALSE, TRUE),
       ('BeanC', TRUE, FALSE);

-- queue_package 테이블에 데이터 삽입
INSERT INTO queue_package (producer_id, consumer_id)
VALUES (1, 2), -- BeanA가 BeanB에 메시지를 전송
       (1, 3), -- BeanA가 BeanC에 메시지를 전송
       (2, 3); -- BeanB가 BeanC에 메시지를 전송
