package mbenamar.finalworkapi.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class FreqChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private Date changeDate;
    private Float oldMinFreq;
    private Float oldMaxFreq;
    private Float newMinFreq;
    private Float newMaxFreq;
    private String type;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public FreqChange() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Float getOldMinFreq() {
        return oldMinFreq;
    }

    public void setOldMinFreq(Float oldMinFreq) {
        this.oldMinFreq = oldMinFreq;
    }

    public Float getOldMaxFreq() {
        return oldMaxFreq;
    }

    public void setOldMaxFreq(Float oldMaxFreq) {
        this.oldMaxFreq = oldMaxFreq;
    }

    public Float getNewMinFreq() {
        return newMinFreq;
    }

    public void setNewMinFreq(Float newMinFreq) {
        this.newMinFreq = newMinFreq;
    }

    public Float getNewMaxFreq() {
        return newMaxFreq;
    }

    public void setNewMaxFreq(Float newMaxFreq) {
        this.newMaxFreq = newMaxFreq;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
