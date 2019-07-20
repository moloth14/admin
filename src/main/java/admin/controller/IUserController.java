package admin.controller;

import admin.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public interface IUserController {

    String USER_PATH = "/api/users";
    String USER_ID_PARAM = "/{id}";

    /**
     * @param user      newly created user as request body parameter
     * @param ucBuilder
     * @return response with new user's location or CONFLICT status if user already exists
     */
    ResponseEntity<User> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder);

    /**
     * @return list of all users
     */
    ResponseEntity<List<User>> getUsers();

    /**
     * @param id user's id
     * @return user found by id or NOT_FOUND status if user not exists
     */
    ResponseEntity<User> getUser(@PathVariable Long id);

    /**
     * @param id   user's id
     * @param user user object with new parameters
     * @return NOT_FOUND status if user not exists, or nothing, correct update does not requires detailed response
     */
    ResponseEntity updateUser(@PathVariable Long id, @RequestBody User user);

    /**
     * @param id id of user to delete
     * @return nothing or NOT_FOUND status if user not exists
     */
    ResponseEntity deleteUser(@PathVariable Long id);
}
