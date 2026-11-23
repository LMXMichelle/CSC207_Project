package UseCase.SignUp;

public interface SignUpOutputBoundary {
    void switchToLoginView();

    void prepareFailView(String message);

    void prepareSuccessView(SignUpOutputData signupoutputdata);
}