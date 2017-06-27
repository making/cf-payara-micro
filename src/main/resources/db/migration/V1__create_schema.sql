CREATE TABLE message (
  id         INT PRIMARY KEY AUTO_INCREMENT,
  text       VARCHAR(255),
  created_at TIMESTAMP       DEFAULT now()
);