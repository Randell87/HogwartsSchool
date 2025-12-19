-- 1. Студенты в возрасте от 10 до 20
SELECT * FROM student WHERE age BETWEEN 10 AND 20;

-- 2. Только имена студентов
SELECT name FROM student;

-- 3. Студенты, в имени которых есть буква 'о' (регистронезависимо)
SELECT * FROM student WHERE LOWER(name) LIKE '%о%';

-- 4. Студенты, у которых возраст меньше id
SELECT * FROM student WHERE age < id;

-- 5. Студенты, отсортированные по возрасту
SELECT * FROM student ORDER BY age;