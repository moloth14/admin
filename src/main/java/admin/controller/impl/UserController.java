package admin.controller.impl;

import admin.controller.IUserController;
import admin.entity.User;
import admin.service.IUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static admin.controller.IUserController.USER_PATH;
import static org.springframework.http.ResponseEntity.*;

/**
 * Controller returning responses for basic CRUDL requests
 */
@RestController
@RequestMapping(USER_PATH)
@Api(value = "User controller api")
public class UserController implements IUserController {

    @Autowired
    private IUserService userService;

    @Override
    @PostMapping
    @ApiOperation(value = "Create a new user")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "User successfully created"),
            @ApiResponse(code = 400, message = "Incorrect request"),
            @ApiResponse(code = 409, message = "User already exists")})
    public ResponseEntity<?> createUser(
            @ApiParam(value = "User to create", required = true, example = "user") @Valid @RequestBody User user,
            UriComponentsBuilder ucBuilder) {
        userService.createUser(user);
        return created(ucBuilder.path(USER_PATH + USER_ID_PARAM)
                               .buildAndExpand(user.getId())
                               .toUri()).build();
    }

    @Override
    @GetMapping
    @ApiOperation(value = "List of all users", response = List.class)
    @ApiResponse(code = 200, message = "Successfully retrieved list")
    public ResponseEntity<List<User>> getUsers() {
        return ok(userService.getUsers());
    }

    @Override
    @GetMapping(USER_ID_PARAM)
    @ApiOperation(value = "Get an user by id", response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully found user"),
            @ApiResponse(code = 404, message = "User not exists")})
    public ResponseEntity<User> getUser(
            @ApiParam(value = "User's id", required = true, example = "1") @PathVariable Long id) {
        return ok(userService.getUser(id));
    }

    @Override
    @PutMapping(USER_ID_PARAM)
    @ApiOperation(value = "Update an user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "User updated"),
            @ApiResponse(code = 400, message = "Incorrect request"),
            @ApiResponse(code = 404, message = "User not exists")})
    public ResponseEntity updateUser(
            @ApiParam(value = "User's id", required = true, example = "1") @PathVariable Long id,
            @ApiParam(value = "User's new params", required = true, example = "user") @Valid @RequestBody User user) {
        userService.updateUser(id, user);
        return ok().build();
    }

    @Override
    @DeleteMapping(USER_ID_PARAM)
    @ApiOperation(value = "Delete an user")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "User deleted"),
            @ApiResponse(code = 404, message = "User not exists")})
    public ResponseEntity deleteUser(
            @ApiParam(value = "User's id", required = true, example = "1") @PathVariable Long id) {
        userService.deleteUser(id);
        return noContent().build();
    }
}
