package admin.repository;

import admin.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Simple repository for taking users from db. There could be added some entity-specific methods, such as findByLogin(),
 * afterwards.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
