package mbenamar.finalworkapi.Controllers;

import mbenamar.finalworkapi.Models.FreqChange;
import mbenamar.finalworkapi.Models.Session;
import mbenamar.finalworkapi.Models.SessionStep;
import mbenamar.finalworkapi.Models.User;
import mbenamar.finalworkapi.Repositories.FreqChangeRepository;
import mbenamar.finalworkapi.Repositories.SessionRepository;
import mbenamar.finalworkapi.Repositories.SessionStepRepository;
import mbenamar.finalworkapi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/web")
public class WebController {

    @Autowired
    private UserRepository users;
    @Autowired
    private SessionRepository sessions;
    @Autowired
    private SessionStepRepository sessionSteps;
    @Autowired
    private FreqChangeRepository freqChanges;

    private final String _adminToken = "password123";

    @GetMapping("/users")
    public Iterable<User> getUsers(@RequestHeader String adminToken)
    {
        if(!adminToken.equals(_adminToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return users.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id, @RequestHeader String adminToken)
    {
        if(!adminToken.equals(_adminToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return users.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/sessions/{userid}")
    public Iterable<Session> getSessions(@PathVariable String userid, @RequestHeader String adminToken)
    {
        if(!adminToken.equals(_adminToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return sessions.findSessionsByUserUsernameEquals(userid);
    }

    @GetMapping("/sessionsteps/{sessionid}")
    public Iterable<SessionStep> getSessionSteps(@PathVariable Long sessionid, @RequestHeader String adminToken)
    {
        if(!adminToken.equals(_adminToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return sessionSteps.findSessionStepBySessionIdEquals(sessionid);
    }

    @GetMapping("/freqchanges/{userid}")
    public Iterable<FreqChange> getFreqChanges(@PathVariable String userid, @RequestHeader String adminToken)
    {
        if(!adminToken.equals(_adminToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return freqChanges.findFreqChangesByUserUsernameEquals(userid);
    }
}
