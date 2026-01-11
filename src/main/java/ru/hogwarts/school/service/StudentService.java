// ru.hogwarts.school.service.StudentService
package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.info("Was invoked method for get student by id = {}", id);
        return studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("There is no student with id = {}", id);
                    throw new IllegalArgumentException("Student not found");
                });
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for update student with id = {}", student.getId());
        if (!studentRepository.existsById(student.getId())) {
            logger.error("Cannot update: no student with id = {}", student.getId());
            throw new IllegalArgumentException("Student not found for update");
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoked method for delete student by id = {}", id);
        if (!studentRepository.existsById(id)) {
            logger.warn("Attempt to delete non-existing student with id = {}", id);
        }
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        logger.debug("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    public Collection<Student> findByAge(int age) {
        logger.debug("Was invoked method for get students by age = {}", age);
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.debug("Was invoked method for get students by age between {} and {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public long getTotalStudents() {
        logger.debug("Was invoked method for get total number of students");
        return studentRepository.countAllStudents();
    }

    public double getAverageAge() {
        logger.debug("Was invoked method for get average age of students");
        Double avg = studentRepository.getAverageAge();
        return avg != null ? avg : 0.0;
    }

    public List<Student> getLastFiveStudents() {
        logger.debug("Was invoked method for get last five students");
        return studentRepository.findLastFiveStudents();
    }
}