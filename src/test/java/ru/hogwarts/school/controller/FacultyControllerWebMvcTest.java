package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createFaculty_shouldReturnCreated() throws Exception {
        Faculty faculty = new Faculty("Ravenclaw", "blue");
        faculty.setId(1L);
        when(facultyService.addFaculty(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Faculty("Ravenclaw", "blue"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ravenclaw"));
    }

    @Test
    void searchFaculty_shouldReturnMatched() throws Exception {
        Faculty f = new Faculty("Hufflepuff", "yellow");
        f.setId(1L);
        when(facultyService.searchByNameOrColor("hufflepuff")).thenReturn(List.of(f));

        mockMvc.perform(get("/faculty/search?query=hufflepuff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hufflepuff"));
    }
}