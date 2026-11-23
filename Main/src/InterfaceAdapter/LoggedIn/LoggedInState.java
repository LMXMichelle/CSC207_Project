package InterfaceAdapter.LoggedIn;

public class LoggedInState {
    private String username = "";

    public LoggedInState(LoggedInState copy) {
        username = copy.username;
    }

    public LoggedInState() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
