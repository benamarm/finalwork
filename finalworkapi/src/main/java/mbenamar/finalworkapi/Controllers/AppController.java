package mbenamar.finalworkapi.Controllers;

import mbenamar.finalworkapi.Models.*;
import mbenamar.finalworkapi.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "*")
public class AppController {

    @Autowired
    private UserRepository users;
    @Autowired
    private SessionRepository sessions;
    @Autowired
    private FreqChangeRepository freqChanges;

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request)
    {
        User user = users.findById(request.getUsername()).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(request.getPassword().equals(user.getPassword()))
            return user;
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{id}")
    public void userFree(@PathVariable String id)
    {
        if(users.existsById(id))
            throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    @PostMapping("/users")
    public void createUser(@RequestBody User u)
    {
        users.save(u);
    }

    @GetMapping("/delete")
    public void deleteUser(@RequestHeader String userToken)
    {
        User user = users.findUserByTokenEquals(userToken).orElseThrow(() ->  new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        users.delete(user);
    }

    @PostMapping("/sessions")
    public void createSession(@RequestBody Session session, @RequestHeader String userToken)
    {
        User user = users.findUserByTokenEquals(userToken).orElseThrow(() ->  new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        session.setUser(user);

        for(SessionStep s : session.getSessionSteps())
        {
            s.setSession(session);
        }

        sessions.save(session);
    }

    @PostMapping("/freqchanges")
    public void createFreqChange(@RequestBody FreqChange freqChange, @RequestHeader String userToken)
    {
        User user = users.findUserByTokenEquals(userToken).orElseThrow(() ->  new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        freqChange.setUser(user);

        freqChanges.save(freqChange);
    }

    @GetMapping("/freqchanges")
    public FreqChange getLatestFreqChange(@RequestHeader String userToken)
    {
        User user = users.findUserByTokenEquals(userToken).orElseThrow(() ->  new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return freqChanges.findFirstByUserTokenEqualsOrderByChangeDateDesc(userToken);
    }
}
