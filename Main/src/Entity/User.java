package Entity;

import java.util.ArrayList;
import Entity.History;

public class User {
    private final String username;
    private final String pw;

    public User(String username, String pw) {
        if ("".equals(username)) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if ("".equals(pw)) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.username = username;
        this.pw = pw;
    }

    public String getName() {
        return username;
    }

    public String getPassword() {
        return pw;
    }

}
