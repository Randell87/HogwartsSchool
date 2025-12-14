package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final Map<Long, Student> students = new HashMap<>();
    private long idCounter = 1;

    public Student addStudent(Student student) {
        student.setId(idCounter++);
        students.put(student.getId(), student);
        return student;
    }

    public Student findStudent(long id) {
        return students.get(id); // может быть null — допустимо в рамках теста
    }

    public Student editStudent(Student student) {
        if (!students.containsKey(student.getId())) {
            return null;
        }
        students.put(student.getId(), student);
        return student;
    }

    public void deleteStudent(long id) {
        students.remove(id);
    }

    public Collection<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public Collection<Student> findByAge(int age) {
        return students.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
}