package admin.controller.impl;

import admin.controller.IUserController;
import admin.entity.User;
import admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static admin.controller.IUserController.USER_PATH;
import static org.springframework.http.ResponseEntity.*;

/**
 * Controller returning responses for basic CRUDL requests
 */
@RestController
@RequestMapping(USER_PATH)
public class UserController implements IUserController {

    @Autowired
    private IUserService userService;

    @Override
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        userService.createUser(user);
        return created(ucBuilder.path(USER_PATH + USER_ID_PARAM)
                               .buildAndExpand(user.getId())
                               .toUri()).build();
    }

    @Override
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ok(userService.getUsers());
    }

    @Override
    @GetMapping(USER_ID_PARAM)
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ok(userService.getUser(id));
    }

    @Override
    @PutMapping(USER_ID_PARAM)
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user);
        return ok().build();
    }

    @Override
    @DeleteMapping(USER_ID_PARAM)
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return noContent().build();
    }
}
