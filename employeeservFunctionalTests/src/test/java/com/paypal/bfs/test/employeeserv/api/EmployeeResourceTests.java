package com.paypal.bfs.test.employeeserv.api;

import com.paypal.bfs.test.employeeserv.EmployeeservApplication;
import com.paypal.bfs.test.employeeserv.entity.AddressEntity;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootTest(classes = EmployeeservApplication.class)
@ActiveProfiles("test")
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class EmployeeResourceTests {


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Nested
    @DisplayName("Employee Get Api Tests")
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    class EmployeeGetTests {

        @BeforeAll
        public void setup() throws ParseException {
            EmployeeEntity employeeEntity = createEmployeeEntity();
            employeeRepository.save(employeeEntity);
        }

        @AfterAll
        public void flush() {
            employeeRepository.flush();
        }

        @Test
        public void Test_getEmployee_200() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/bfs/employees/1").accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        //Testing by sending employeeId that doesn't exist
        @Test
        public void Test_getEmployee_404() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/bfs/employees/4").accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        //Testing by sending employeeId to String instead of Long
        @Test
        public void Test_getEmployee_500() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/bfs/employees/hell").accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        }

        public EmployeeEntity createEmployeeEntity() throws ParseException {
            EmployeeEntity employeeEntity = new EmployeeEntity();
            employeeEntity.setId(1L);
            employeeEntity.setFirstName("Test");
            employeeEntity.setLastName("Test");
            employeeEntity.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1996-02-25"));
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setLine1("Line1 Test");
            addressEntity.setLine2("Line2 Test");
            addressEntity.setCountry("country Test");
            addressEntity.setZipCode("zipcode Test");
            addressEntity.setState("state Test");
            addressEntity.setCity("city Test");
            employeeEntity.setAddress(addressEntity);
            return employeeEntity;


        }
    }

    @Nested
    @DisplayName("Employee Post Api Tests")
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    class EmployeePostTests {

        @Test
        public void Test_postEmployee_201() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/v1/bfs/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(testResourceFile("request/post_employee_201.json")))
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        @Test
        public void Test_postEmployee_400_first_name_null() throws Exception{

            mockMvc.perform(MockMvcRequestBuilders.post("/v1/bfs/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(testResourceFile("request/post_employee_400_first_name.json")))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(testResourceFile("/response/post_employee_400_first_name.json")));

        }

        @Test
        public void Test_postEmployee_400_last_name_null() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.post("/v1/bfs/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(testResourceFile("request/post_employee_400_last_name.json")))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(testResourceFile("/response/post_employee_400_last_name.json")));

        }

        @Test
        public void Test_postEmployee_400_date_of_birth_null() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.post("/v1/bfs/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(testResourceFile("request/post_employee_400_dob.json")))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(testResourceFile("/response/post_employee_400_dob.json")));

        }

        @Test
        public void Test_postEmployee_400_line1_null() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.post("/v1/bfs/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(testResourceFile("request/post_employee_400_line1.json")))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(testResourceFile("/response/post_employee_400_line1.json")));

        }

        @Test
        public void Test_postEmployee_201_line2_null() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.post("/v1/bfs/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(testResourceFile("request/post_employee_201_line2.json")))
                    .andExpect(MockMvcResultMatchers.status().isCreated());

        }
    }

    public static String testResourceFile(String filePath) throws IOException {
        File resource = new ClassPathResource(filePath).getFile();
        return new String(Files.readAllBytes(resource.toPath()));
    }
}
