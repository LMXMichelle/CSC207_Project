package UseCase.SignUp;

public interface SignUpInputBoundary {
    void execute(SignUpInputData signupInputData);

    void switchToLoginView();
}
