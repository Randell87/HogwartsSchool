package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StudentService {

    private final Map<Long, Student> students = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Student createStudent(Student student) {
        Long id = idCounter.getAndIncrement();
        student.setId(id);
        students.put(id, student);
        return student;
    }

    public Student getStudent(Long id) {
        return students.get(id);
    }

    public Student updateStudent(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public void deleteStudent(Long id) {
        students.remove(id);
    }

    public Collection<Student> getAllStudents() {
        return students.values();
    }

    public Collection<Student> getStudentsByAge(int age) {
        return students.values().stream()
                .filter(s -> s.getAge() == age)
                .toList();
    }
}
