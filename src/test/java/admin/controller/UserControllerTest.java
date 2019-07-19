package admin.controller;

import admin.UserApplication;
import admin.entity.User;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;

import static java.time.LocalDate.of;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for CRUD controller methods, based on MockMvc requests.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    public void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters)
                .stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .get();
        assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    /**
     * Mock table is empty, we expect correct result of findAll() method
     */
    @Test
    public void emptyListTest() throws Exception {
        mockMvc.perform(get("/api/users").contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * we awaiting custom exception in this case
     */
    @Test
    public void userNotFoundTest() throws Exception {
        mockMvc.perform(get("/api/users/1").contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void basicCasesTest() throws Exception {
        User[] users = new User[4];

        // testing user creation
        for (int i = 1; i <= 3; i++) {
            users[i] = createUser(i);
            createUserTest(i, users[i]);
        }

        // testing finding user by id
        for (int i = 1; i <= 3; i++)
            getUserTest(i, i);

        // testing updating user's fields
        User newUser = createUser(4);
        mockMvc.perform(put("/api/users/3").contentType(APPLICATION_JSON)
                                .content(json(newUser)))
                .andExpect(status().isOk());
        // after updating user with id 3 must have fields ending by 4
        getUserTest(3, 4);

        // testing deleting user by id
        mockMvc.perform(delete("/api/users/2").contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/users/2").contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // testing retrieving all users (1 and 3)
        mockMvc.perform(get("/api/users").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", contains(1, 3)));
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    private User createUser(int i) {
        return new User(Long.valueOf(i), "name" + i, "surname" + i, of(2000, 1, i), "login" + i, "password" + i,
                        "personalInfo" + i, "address" + i);
    }

    private User createUserTest(int i, User user) throws Exception {
        mockMvc.perform(post("/api/users").contentType(APPLICATION_JSON)
                                .content(json(user)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is("http://localhost/api/users/" + i)));
        return user;
    }

    private void getUserTest(int id, int valueIndex) throws Exception {
        Matcher<String> m = endsWith(String.valueOf(valueIndex));
        mockMvc.perform(get("/api/users/" + id).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", m))
                .andExpect(jsonPath("$.surname", m))
                .andExpect(jsonPath("$.birthDate", m))
                .andExpect(jsonPath("$.login", m))
                .andExpect(jsonPath("$.password", m))
                .andExpect(jsonPath("$.personalInfo", m))
                .andExpect(jsonPath("$.address", m));
    }
}
