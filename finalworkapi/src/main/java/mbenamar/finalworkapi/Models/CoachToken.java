package mbenamar.finalworkapi.Models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CoachToken {

    @Id
    private String token;

    public CoachToken() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
