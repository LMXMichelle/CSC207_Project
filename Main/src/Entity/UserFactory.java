package Entity;

public class UserFactory {

    public User create(String username, String pw) {
        return new User(username, pw);
    }

}
