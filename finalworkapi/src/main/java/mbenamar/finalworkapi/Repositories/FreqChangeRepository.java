package mbenamar.finalworkapi.Repositories;

import mbenamar.finalworkapi.Models.FreqChange;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FreqChangeRepository extends CrudRepository<FreqChange, Long> {
    Iterable<FreqChange> findFreqChangesByUserUsernameEquals(String username);
    FreqChange findFirstByUserTokenEqualsOrderByChangeDateDesc(String username);
}
