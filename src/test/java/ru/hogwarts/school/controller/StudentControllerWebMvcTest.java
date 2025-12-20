package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createStudent_shouldReturnCreatedStudent() throws Exception {
        Student student = new Student("Harry", 12);
        student.setId(1L);
        when(studentService.addStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Student("Harry", 12))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Harry"))
                .andExpect(jsonPath("$.age").value(12));
    }

    @Test
    void getStudent_shouldReturnStudent() throws Exception {
        Student student = new Student("Hermione", 13);
        student.setId(1L);
        when(studentService.findStudent(1L)).thenReturn(student);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hermione"));
    }

    @Test
    void getStudent_notFound_shouldReturn404() throws Exception {
        when(studentService.findStudent(999L)).thenReturn(null);

        mockMvc.perform(get("/student/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getStudentsByAgeBetween_shouldReturnFiltered() throws Exception {
        Student s1 = new Student("Neville", 12);
        s1.setId(1L);
        when(studentService.findByAgeBetween(12, 12)).thenReturn(List.of(s1));

        mockMvc.perform(get("/student/age?min=12&max=12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Neville"));
    }
}