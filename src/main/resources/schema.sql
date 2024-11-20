CREATE TABLE Question (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          text VARCHAR(255),
                          questionType VARCHAR(255) DEFAULT 'radio'
);
