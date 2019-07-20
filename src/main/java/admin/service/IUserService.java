package admin.service;

import admin.entity.User;

import java.util.List;

/**
 * Service layer for requests
 */
public interface IUserService {

    void createUser(User user);

    List<User> getUsers();

    User getUser(Long id);

    void updateUser(Long id, User user);

    void deleteUser(Long id);
}
