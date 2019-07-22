package admin.service.impl;

import admin.entity.User;
import admin.exception.UserAlreadyExistsException;
import admin.exception.UserNotFoundException;
import admin.repository.UserRepository;
import admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.nio.CharBuffer.wrap;

/**
 * Implementation of service. Contains backend realization of methods.
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(User user) {
        Long id = user.getId();
        if (userRepository.findById(id)
                .isPresent())
            throw new UserAlreadyExistsException(id);
        encodePassword(user);
        userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        users.addAll((Collection<User>) userRepository.findAll());
        return users;
    }

    @Override
    public User getUser(Long id) {
        return findUserById(id);
    }

    @Override
    public void updateUser(Long id, User user) {
        User userToUpdate = findUserById(id);
        userToUpdate.setFields(user.getName(), user.getSurname(), user.getBirthDate(), user.getLogin(),
                               user.getPassword(), user.getPersonalInfo(), user.getAddress());
        encodePassword(userToUpdate);
        userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(findUserById(id));
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(wrap(user.getPassword()))
                                 .toCharArray());
    }
}
