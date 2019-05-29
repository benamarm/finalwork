package mbenamar.finalworkapi.Controllers;

import mbenamar.finalworkapi.Models.*;
import mbenamar.finalworkapi.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    private SessionAudioRepository audios;

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request)
    {
        User user = users.findById(request.getUsername()).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND));

        //User should not be able to access notes written by coach
        user.setNote("");

        if(BCrypt.checkpw(request.getPassword(), user.getPassword()))
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
        u.setPassword(BCrypt.hashpw(u.getPassword(), BCrypt.gensalt()));
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

    @GetMapping("/feedback")
    public void newFeedback(@RequestHeader String userToken)
    {
        User user = users.findUserByTokenEquals(userToken).orElseThrow(() ->  new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if(!sessions.findSessionsByUserUsernameEqualsAndFeedbackSeenEquals(user.getUsername(), false).iterator().hasNext())
            throw new ResponseStatusException(HttpStatus.CONFLICT);
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

    @PostMapping("/uploadAudio")
    public void uploadAudio(@RequestHeader String userToken, HttpServletRequest requestEntity)
    {
        User user = users.findUserByTokenEquals(userToken).orElseThrow(() ->  new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        Session s = sessions.findFirstByUserUsernameEqualsOrderByIdDesc(user.getUsername());
        SessionAudio audio = new SessionAudio();
        try {
            audio.setAudio(IOUtils.toByteArray(requestEntity.getInputStream()));
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        audio.setSession(s);

        audios.save(audio);
    }

    @Bean
    public FilterRegistrationBean registration(HiddenHttpMethodFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }
}
