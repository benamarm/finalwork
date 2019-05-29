package mbenamar.finalworkapi.Repositories;

import mbenamar.finalworkapi.Models.SessionAudio;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionAudioRepository extends CrudRepository<SessionAudio, Long> {
    Optional<SessionAudio> findSessionAudioBySessionIdEquals(Long id);
}
