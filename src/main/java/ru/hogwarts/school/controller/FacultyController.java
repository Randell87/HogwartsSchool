package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @GetMapping("/{id}")
    public Faculty getFaculty(@PathVariable long id) {
        return facultyService.findFaculty(id);
    }

    @PutMapping
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping
    public Collection<Faculty> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("/filter")
    public Collection<Faculty> getFacultiesByColor(@RequestParam String color) {
        return facultyService.findByColor(color);
    }

    @GetMapping("/search")
    public Collection<Faculty> searchFaculties(@RequestParam String query) {
        return facultyService.searchByNameOrColor(query);
    }

    @GetMapping("/{id}/students")
    public Collection<Student> getFacultyStudents(@PathVariable Long id) {
        return facultyService.getFacultyStudents(id);
    }
}