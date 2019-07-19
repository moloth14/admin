package admin.controller;

import admin.entity.User;
import admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

/**
 * Controller returning responses for basic CRUDL requests
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @param user      newly created user as request body parameter
     * @param ucBuilder
     * @return response with new user's location or CONFLICT status if user already exists
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        userService.createUser(user);
        return created(ucBuilder.path("/api/users/{id}")
                               .buildAndExpand(user.getId())
                               .toUri()).build();
    }

    /**
     * @return list of all users
     */
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ok(userService.getUsers());
    }

    /**
     * @param id user's id
     * @return user found by id or NOT_FOUND status if user not exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ok(userService.getUser(id));
    }

    /**
     * @param id   user's id
     * @param user user object with new parameters
     * @return NOT_FOUND status if user not exists, or nothing, correct update does not requires detailed response
     */
    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user);
        return ok().build();
    }

    /**
     * @param id id of user to delete
     * @return nothing or NOT_FOUND status if user not exists
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return noContent().build();
    }
}
