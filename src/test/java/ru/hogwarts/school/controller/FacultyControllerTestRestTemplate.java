package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/faculty";
    }

    @Test
    void createFaculty_shouldReturnCreatedFaculty() {
        Faculty faculty = new Faculty("Gryffindor", "red");

        ResponseEntity<Faculty> response = restTemplate.postForEntity(baseUrl(), faculty, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Gryffindor");
    }

    @Test
    void getFaculty_notFound_shouldReturn404() {
        ResponseEntity<Faculty> response = restTemplate.getForEntity(baseUrl() + "/999999", Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void searchFaculty_shouldReturnMatchedByName() {
        restTemplate.postForObject(baseUrl(), new Faculty("Slytherin", "green"), Faculty.class);

        ResponseEntity<Faculty[]> response = restTemplate.getForEntity(
                baseUrl() + "/search?query=slytherin", Faculty[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()[0].getName()).isEqualTo("Slytherin");
    }
}
