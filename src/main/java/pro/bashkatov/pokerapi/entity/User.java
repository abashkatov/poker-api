package pro.bashkatov.pokerapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    String id = null;
    String password = null;
    String username = null;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
