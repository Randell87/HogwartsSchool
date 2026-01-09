-- 1. Возраст студента >= 16
ALTER TABLE student
    ADD CONSTRAINT check_age CHECK (age >= 16);

-- 2. Имя студента — NOT NULL и UNIQUE
ALTER TABLE student
    ALTER COLUMN name SET NOT NULL,
    ADD CONSTRAINT unique_name UNIQUE (name);

-- 3. Пара (name, color) в faculty — уникальна
ALTER TABLE faculty
    ADD CONSTRAINT unique_name_color UNIQUE (name, color);

-- 4. DEFAULT возраст = 20
ALTER TABLE student
    ALTER COLUMN age SET DEFAULT 20;