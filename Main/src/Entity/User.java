package Entity;

import java.util.ArrayList;
import Entity.History;

public class User {
    private final String username;
    private final String pw;
    private History history;

    public User(String username, String pw) {
        if ("".equals(username)) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if ("".equals(pw)) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.username = username;
        this.pw = pw;
        this.history = new History();
    }

    public String getUserName() {
        return username;
    }

    public String getPw() {
        return pw;
    }

    public History getHistory() {
        return history;
    }

}
