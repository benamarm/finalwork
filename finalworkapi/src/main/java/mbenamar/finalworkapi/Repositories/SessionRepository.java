package mbenamar.finalworkapi.Repositories;

import mbenamar.finalworkapi.Models.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {
    Iterable<Session> findSessionsByUserUsernameEquals(String username);
}
