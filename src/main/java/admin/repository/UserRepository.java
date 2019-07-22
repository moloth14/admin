package admin.repository;

import admin.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.util.stream.StreamSupport.stream;

/**
 * Simple repository for taking users from db. There could be added some entity-specific methods, such as findByLogin(),
 * afterwards.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * @param login
     * @return user found by unique login if exists
     */
    default Optional<User> findByLogin(String login) {
        return stream(findAll().spliterator(), false).filter(user -> user.getLogin()
                .equals(login))
                .findAny();
    }

}
