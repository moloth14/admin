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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Arrays;

import static admin.controller.IUserController.USER_PATH;
import static java.time.LocalDate.of;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for CRUD controller methods, based on MockMvc requests.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
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
        mockMvc.perform(get(USER_PATH).contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
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
        mockMvc.perform(put(USER_PATH + "/3").contentType(APPLICATION_JSON)
                                .content(json(newUser)))
                .andExpect(status().isOk());
        // after updating user with id 3 must have fields ending by 4
        getUserTest(3, 4);

        // testing deleting user by id
        mockMvc.perform(delete(USER_PATH + "/2").contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mockMvc.perform(get(USER_PATH + "/2").contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // testing retrieving all users (1 and 3)
        mockMvc.perform(get(USER_PATH).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", contains(1, 3)));
    }

    @Test
    public void errorCasesTest() throws Exception {
        User user = createUser(1);

        // test user already exists case
        mockMvc.perform(post(USER_PATH).contentType(APPLICATION_JSON)
                                .content(json(user)));
        mockMvc.perform(post(USER_PATH).contentType(APPLICATION_JSON)
                                .content(json(user)))
                .andExpect(status().isConflict());

        // test user not found cases
        mockMvc.perform(get(USER_PATH + "/2").contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(put(USER_PATH + "/2").contentType(APPLICATION_JSON)
                                .content(json(user)))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete(USER_PATH + "/2").contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // test for non-nullable and not-blank fields
        User newUser = createUser(2);
        newUser.setName("");
        updateUserTest(newUser);
        newUser.setName("user2");
        newUser.setSurname("");
        updateUserTest(newUser);
        newUser.setSurname("surname2");
        newUser.setLogin("");
        updateUserTest(newUser);

        // test for unique fields
        newUser.setLogin("login1");
        mockMvc.perform(post(USER_PATH).contentType(APPLICATION_JSON)
                                .content(json(newUser)))
                .andExpect(status().isBadRequest());
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

    private void createUserTest(int i, User user) throws Exception {
        mockMvc.perform(post(USER_PATH).contentType(APPLICATION_JSON)
                                .content(json(user)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is("http://localhost/api/users/" + i)));
    }

    private void getUserTest(int id, int valueIndex) throws Exception {
        Matcher<String> m = endsWith(String.valueOf(valueIndex));
        mockMvc.perform(get(USER_PATH + "/" + id).contentType(APPLICATION_JSON))
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

    private void updateUserTest(User user) throws Exception {
        mockMvc.perform(put(USER_PATH + "/1").contentType(APPLICATION_JSON)
                                .content(json(user)))
                .andExpect(status().isBadRequest());
    }
}
