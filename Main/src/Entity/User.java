package Entity;

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
    }

    public String getUserName() {
        return username;
    }

    public String getPW() {
        return pw;
    }

}
