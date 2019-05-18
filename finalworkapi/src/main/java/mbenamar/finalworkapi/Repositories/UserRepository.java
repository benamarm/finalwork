package mbenamar.finalworkapi.Repositories;

import mbenamar.finalworkapi.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findUserByTokenEquals(String token);
}
