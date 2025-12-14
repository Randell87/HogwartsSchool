package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FacultyService {

    private final Map<Long, Faculty> faculties = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Faculty createFaculty(Faculty faculty) {
        Long id = idCounter.getAndIncrement();
        faculty.setId(id);
        faculties.put(id, faculty);
        return faculty;
    }

    public Faculty getFaculty(Long id) {
        return faculties.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public void deleteFaculty(Long id) {
        faculties.remove(id);
    }

    public Collection<Faculty> getAllFaculties() {
        return faculties.values();
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        return faculties.values().stream()
                .filter(f -> f.getColor().equalsIgnoreCase(color))
                .toList();
    }
}