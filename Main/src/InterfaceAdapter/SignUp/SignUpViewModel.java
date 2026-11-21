package InterfaceAdapter.SignUp;

import java.util.ArrayList;
import java.util.List;

public class SignUpViewModel {

    public static final String TITLE_LABEL = "Sudoku207 Sign Up";
    public static final String USERNAME_LABEL = "Pick a username";
    public static final String PW_LABEL = "Pick a password";
    public static final String REPEAT_PW_LABEL = "Enter password again";

    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public static final String TO_LOGIN_BUTTON_LABEL = "Login";

    private final List<SignUpModelListener> listeners = new ArrayList<>();

    public static final String VIEW_NAME = "Sign up";

    private SignUpState state = new SignUpState();

    public SignUpState getState() {
        return this.state;
    }

    public void setState(SignUpState state) {
        this.state = state;
    }

    public void addListener(SignUpModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (SignUpModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}

