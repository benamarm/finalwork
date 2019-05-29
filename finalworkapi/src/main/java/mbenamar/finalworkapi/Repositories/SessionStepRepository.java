package mbenamar.finalworkapi.Repositories;

import mbenamar.finalworkapi.Models.SessionStep;
import org.springframework.data.repository.CrudRepository;

public interface SessionStepRepository extends CrudRepository<SessionStep, Long> {
    Iterable<SessionStep> findSessionStepsBySessionIdEquals(Long id);
}
