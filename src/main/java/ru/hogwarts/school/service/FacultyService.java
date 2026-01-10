// src/main/java/ru/hogwarts/school/service/FacultyService.java
package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        logger.info("Was invoked method for get faculty by id = {}", id);
        return facultyRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("There is no faculty with id = {}", id);
                    throw new IllegalArgumentException("Faculty not found");
                });
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty with id = {}", faculty.getId());
        if (!facultyRepository.existsById(faculty.getId())) {
            logger.error("Cannot update: no faculty with id = {}", faculty.getId());
            throw new IllegalArgumentException("Faculty not found for update");
        }
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        logger.info("Was invoked method for delete faculty by id = {}", id);
        if (!facultyRepository.existsById(id)) {
            logger.warn("Attempt to delete non-existing faculty with id = {}", id);
        }
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.debug("Was invoked method for get all faculties");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByColor(String color) {
        logger.debug("Was invoked method for get faculties by color = '{}'", color);
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> searchByNameOrColor(String query) {
        logger.debug("Was invoked method for search faculties by name or color = '{}'", query);
        return facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(query, query);
    }
}