package mbenamar.finalworkapi.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float minFreq;
    private Float maxFreq;
    private Integer score;
    private Date startTime;
    @Column(columnDefinition = "TEXT")
    private String feedback;
    private boolean feedbackSeen;
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<SessionStep> sessionSteps = new HashSet<>();
    @JsonIgnore
    @OneToOne(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private SessionAudio sessionAudio;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public Session() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMinFreq() {
        return minFreq;
    }

    public void setMinFreq(Float minFreq) {
        this.minFreq = minFreq;
    }

    public Float getMaxFreq() {
        return maxFreq;
    }

    public void setMaxFreq(Float maxFreq) {
        this.maxFreq = maxFreq;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonIgnore
    public Set<SessionStep> getSessionSteps() {
        return sessionSteps;
    }

    @JsonProperty
    public void setSessionSteps(Set<SessionStep> sessionSteps) {
        this.sessionSteps = sessionSteps;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SessionAudio getSessionAudio() {
        return sessionAudio;
    }

    public void setSessionAudio(SessionAudio sessionAudio) {
        this.sessionAudio = sessionAudio;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public boolean isFeedbackSeen() {
        return feedbackSeen;
    }

    public void setFeedbackSeen(boolean feedbackSeen) {
        this.feedbackSeen = feedbackSeen;
    }
}
