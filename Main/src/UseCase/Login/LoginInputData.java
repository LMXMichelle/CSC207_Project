package UseCase.Login;

public class LoginInputData {

    private final String username;
    private final String pw;

    public LoginInputData(String username, String pw) {
        this.username = username;
        this.pw = pw;
    }

    String getUsername() {
        return username;
    }

    String getPw() {
        return pw;
    }

}