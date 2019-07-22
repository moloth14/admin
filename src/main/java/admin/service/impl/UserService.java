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

import static org.apache.logging.log4j.util.Strings.EMPTY;

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
        String login = user.getLogin();
        if (userRepository.findById(id)
                .isPresent() || userRepository.findByLogin(login)
                .isPresent())
            throw new UserAlreadyExistsException(id, login);
        encodePassword(user, user.getPassword());
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
                               user.getPersonalInfo(), user.getAddress());
        encodePassword(userToUpdate, user.getPassword());
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

    // if password is null, set empty, if password is empty, leave previous or empty
    private void encodePassword(User user, String password) {
        if (password == null)
            user.setPassword(EMPTY);
        else if (!password.isEmpty())
            user.setPassword(passwordEncoder.encode(password));
    }
}
