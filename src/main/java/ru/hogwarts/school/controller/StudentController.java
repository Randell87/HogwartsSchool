package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Comparator;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.findStudent(id);
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student) {
        return studentService.editStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/filter")
    public Collection<Student> getStudentsByAge(@RequestParam int age) {
        return studentService.findByAge(age);
    }

    @GetMapping("/age")
    public Collection<Student> getStudentsBetweenAge(
            @RequestParam int min,
            @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/{id}/faculty")
    public Faculty getStudentFaculty(@PathVariable Long id) {
        return studentService.getStudentFaculty(id);
    }

    @GetMapping("/total")
    public long getTotalStudents() {
        return studentService.getTotalStudents();
    }

    @GetMapping("/average-age")
    public double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/last-five")
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("/names-start-with-a")
    public List<String> getStudentNamesStartWithA() {
        return studentService.getAllStudents().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith("A"))
                .sorted()
                .toList();
    }

    @GetMapping("/average-age-stream")
    public double getAverageAgeUsingStream() {
        return studentService.getAllStudents().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }

    @GetMapping("/fast-sum")
    public long getFastSum() {
        return IntStream.rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
    }

    @GetMapping("/students/print-parallel")
    public void printStudentsParallel() {
        List<Student> students = new ArrayList<>(studentService.getAllStudents());
        int size = Math.min(6, students.size());
        if (size == 0) return;

        // Основной поток: 1–2
        for (int i = 0; i < Math.min(2, size); i++) {
            System.out.println(students.get(i).getName());
        }

        // Поток 1: 3–4
        if (size > 2) {
            Thread thread1 = new Thread(() -> {
                for (int i = 2; i < Math.min(4, size); i++) {
                    System.out.println(students.get(i).getName());
                }
            });
            thread1.start();
        }

        // Поток 2: 5–6
        if (size > 4) {
            Thread thread2 = new Thread(() -> {
                for (int i = 4; i < size; i++) {
                    System.out.println(students.get(i).getName());
                }
            });
            thread2.start();
        }
    }

    private synchronized void printName(String name) {
        System.out.println(name);
    }

    @GetMapping("/students/print-synchronized")
    public void printStudentsSynchronized() {
        List<Student> students = new ArrayList<>(studentService.getAllStudents());
        int size = Math.min(6, students.size());
        if (size == 0) return;

        // Основной поток: 1–2
        for (int i = 0; i < Math.min(2, size); i++) {
            printName(students.get(i).getName());
        }

        // Поток 1: 3–4
        if (size > 2) {
            Thread thread1 = new Thread(() -> {
                for (int i = 2; i < Math.min(4, size); i++) {
                    printName(students.get(i).getName());
                }
            });
            thread1.start();
        }

        // Поток 2: 5–6
        if (size > 4) {
            Thread thread2 = new Thread(() -> {
                for (int i = 4; i < size; i++) {
                    printName(students.get(i).getName());
                }
            });
            thread2.start();
        }
    }
}