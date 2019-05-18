package mbenamar.finalworkapi.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class SessionStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer seqNum;
    private Float minFreq;
    private Float maxFreq;
    private Float hitFreq;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Session session;

    public SessionStep() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
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

    public Float getHitFreq() {
        return hitFreq;
    }

    public void setHitFreq(Float hitFreq) {
        this.hitFreq = hitFreq;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
