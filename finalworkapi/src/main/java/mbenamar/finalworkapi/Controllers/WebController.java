package mbenamar.finalworkapi.Controllers;

import mbenamar.finalworkapi.Models.*;
import mbenamar.finalworkapi.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/web")
@CrossOrigin(origins = "*")
public class WebController {

    @Autowired
    private UserRepository users;
    @Autowired
    private SessionRepository sessions;
    @Autowired
    private SessionStepRepository sessionSteps;
    @Autowired
    private FreqChangeRepository freqChanges;
    @Autowired
    private CoachTokenRepository coachTokens;
    @Autowired
    private SessionAudioRepository audios;

    private final String _adminToken = "password123";

    @PostMapping("/login")
    public String login(@RequestBody CoachToken coachToken)
    {
        return (coachTokens.existsById(coachToken.getToken()) ? _adminToken : "");
    }

    @GetMapping("/users")
    public Iterable<User> getUsers(@RequestHeader String adminToken)
    {
        if(!adminToken.equals(_adminToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return users.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id, @RequestHeader(required = false) String adminToken, @RequestHeader(required = false) String userToken)
    {
        if(!checkMultiAuthPage(adminToken, userToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return users.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/sessions/{userid}")
    public Iterable<Session> getSessions(@PathVariable String userid, @RequestHeader(required = false) String adminToken, @RequestHeader(required = false) String userToken)
    {
        if(!checkMultiAuthPage(adminToken, userToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return sessions.findSessionsByUserUsernameEquals(userid);
    }

    @GetMapping("/sessionSteps/{sessionid}")
    public Iterable<SessionStep> getSessionSteps(@PathVariable Long sessionid, @RequestHeader(required = false) String adminToken, @RequestHeader(required = false) String userToken)
    {
        if(!checkMultiAuthPage(adminToken, userToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return sessionSteps.findSessionStepsBySessionIdEquals(sessionid);
    }

    @GetMapping("/freqChanges/{userid}")
    public Iterable<FreqChange> getFreqChanges(@PathVariable String userid, @RequestHeader String adminToken)
    {
        if(!adminToken.equals(_adminToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return freqChanges.findFreqChangesByUserUsernameEquals(userid);
    }

    @GetMapping("/latestSession/{userid}")
    public String getLatestSession(@PathVariable String userid, @RequestHeader String adminToken)
    {
        if(!adminToken.equals(_adminToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        Session s = sessions.findFirstByUserUsernameEqualsOrderByIdDesc(userid);

        return (s == null ? null : s.getStartTime().toString());
    }

    @PostMapping("/editNote")
    public void editNote(@RequestHeader String adminToken, @RequestBody User emptyUser)
    {
        if(!adminToken.equals(_adminToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        User fullUser = users.findById(emptyUser.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        fullUser.setNote(emptyUser.getNote());

        users.save(fullUser);
    }

    @PostMapping("/editFeedback")
    public void editFeedback(@RequestHeader String adminToken, @RequestBody Session emptySession)
    {
        if(!adminToken.equals(_adminToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        Session fullSession = sessions.findById(emptySession.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        fullSession.setFeedback(emptySession.getFeedback());
        fullSession.setFeedbackSeen(false);

        sessions.save(fullSession);
    }

    @GetMapping("/feedbackSeen/{sessionid}")
    public void feedbackSeen(@PathVariable Long sessionid, @RequestHeader String userToken)
    {
        users.findUserByTokenEquals(userToken).orElseThrow(() ->  new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        Session s = sessions.findById(sessionid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        s.setFeedbackSeen(true);
        sessions.save(s);
    }

    @GetMapping("/audio/{sessionid}")
    public byte[] getAudio(@PathVariable Long sessionid, @RequestHeader(required = false) String adminToken, @RequestHeader(required = false) String userToken)
    {
        if(!checkMultiAuthPage(adminToken, userToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        SessionAudio audio = audios.findSessionAudioBySessionIdEquals(sessionid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return audio.getAudio();
    }

    private boolean checkMultiAuthPage(String adminToken, String userToken)
    {
        User u = users.findUserByTokenEquals(userToken).orElse(null);
        //If incorrect admin token and user token doesn't exist
        if(!_adminToken.equals(adminToken) && u == null)
            return false;

        return true;
    }

}
