package admin.service.impl;

import admin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import admin.repository.UserRepository;
import admin.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        users.addAll((Collection<User>) userRepository.findAll());
        return users;
    }
}
