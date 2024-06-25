DROP TABLE IF EXISTS orders;

CREATE TABLE orders (
                           id INT AUTO_INCREMENT  PRIMARY KEY,
                           customer_id LONG NOT NULL,
                           order_number LONG NOT NULL
);

INSERT INTO orders (customer_id, order_number) VALUES (1, 10001),
                                                      (2, 10002),
                                                      (3, 10003),
                                                      (4, 10004),
                                                      (5, 10005);