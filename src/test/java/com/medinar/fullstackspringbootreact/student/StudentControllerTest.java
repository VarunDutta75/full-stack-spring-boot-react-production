package com.medinar.fullstackspringbootreact.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import java.util.Collections;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void shouldReturnEmptyListWhenNoStudentsExist() throws Exception {
        when(studentService.getAllStudents())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
    @Test
void shouldReturnStudentListAsJson() throws Exception {
    Student student = new Student();
    student.setName("Rahul");
    student.setEmail("rahul@example.com");

    when(studentService.getAllStudents())
            .thenReturn(java.util.List.of(student));

    mockMvc.perform(get("/api/v1/students"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Rahul"))
            .andExpect(jsonPath("$[0].email").value("rahul@example.com"));
}
@Test
void shouldRejectInvalidStudentRequest() throws Exception {
    mockMvc.perform(post("/api/v1/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"\",\"email\":\"invalid\"}"))
            .andExpect(status().isBadRequest());

    verifyNoInteractions(studentService);
}
@Test
void shouldAcceptValidStudentRequest() throws Exception {
    mockMvc.perform(post("/api/v1/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "name": "Rahul",
                    "email": "rahul@example.com",
                    "gender": "MALE"
                }
                """))
            .andExpect(status().isOk());

    verify(studentService).addStudent(
            org.mockito.ArgumentMatchers.any(Student.class));
}
}