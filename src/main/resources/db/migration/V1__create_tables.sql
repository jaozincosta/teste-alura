-- Criação da tabela de cursos
CREATE TABLE course (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        status VARCHAR(20) NOT NULL,
                        published_at TIMESTAMP NULL
);

-- Criação da tabela de atividades
CREATE TABLE task (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      statement VARCHAR(255) NOT NULL,
                      activity_type VARCHAR(20) NOT NULL,
                      order_number INT NOT NULL,
                      course_id BIGINT NOT NULL,
                      FOREIGN KEY (course_id) REFERENCES course(id)
);

-- Criação da tabela de opções para atividades de escolha
CREATE TABLE option_task (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             option_text VARCHAR(80) NOT NULL,
                             is_correct BOOLEAN NOT NULL,
                             task_id BIGINT NOT NULL,
                             FOREIGN KEY (task_id) REFERENCES task(id)
);
