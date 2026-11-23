package UseCase.SignUp;

public class SignUpInputData {

    private final String username;
    private final String pw;
    private final String repeatpw;

    public SignUpInputData(String username, String pw, String repeatpw) {
        this.username = username;
        this.pw = pw;
        this.repeatpw = repeatpw;
    }

    public String getUsername() {
        return username;
    }

    public String getPw() {
        return pw;
    }

    public String getRepeatpw() {
        return repeatpw;
    }
}
