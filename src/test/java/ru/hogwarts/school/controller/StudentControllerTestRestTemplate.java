package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/student";
    }

    @Test
    void createStudent_shouldReturnCreatedStudent() {
        Student student = new Student("Harry Potter", 12);

        ResponseEntity<Student> response = restTemplate.postForEntity(
                baseUrl(), student, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Harry Potter");
        assertThat(response.getBody().getAge()).isEqualTo(12);
    }

    @Test
    void getStudent_shouldReturnStudent() {
        // Сначала создаём
        Student created = restTemplate.postForObject(baseUrl(), new Student("Hermione", 13), Student.class);
        long id = created.getId();

        // Затем получаем
        ResponseEntity<Student> response = restTemplate.getForEntity(baseUrl() + "/" + id, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Hermione");
    }

    @Test
    void getStudent_notFound_shouldReturn404() {
        ResponseEntity<Student> response = restTemplate.getForEntity(baseUrl() + "/999999", Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateStudent_shouldReturnUpdatedStudent() {
        Student created = restTemplate.postForObject(baseUrl(), new Student("Ron", 12), Student.class);
        Student updated = new Student("Ron Weasley", 13);
        updated.setId(created.getId());

        HttpEntity<Student> request = new HttpEntity<>(updated);
        ResponseEntity<Student> response = restTemplate.exchange(
                baseUrl(), HttpMethod.PUT, request, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Ron Weasley");
    }

    @Test
    void deleteStudent_shouldRemoveStudent() {
        Student created = restTemplate.postForObject(baseUrl(), new Student("Draco", 12), Student.class);
        long id = created.getId();

        restTemplate.delete(baseUrl() + "/" + id);
        ResponseEntity<Student> response = restTemplate.getForEntity(baseUrl() + "/" + id, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getStudentsByAgeBetween_shouldReturnFilteredStudents() {
        restTemplate.postForObject(baseUrl(), new Student("Neville", 12), Student.class);
        restTemplate.postForObject(baseUrl(), new Student("Luna", 13), Student.class);

        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                baseUrl() + "/age?min=12&max=12", Student[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()[0].getName()).isEqualTo("Neville");
    }
}
