package UseCase.Login;

public interface LoginInputBoundary {
    void execute(LoginInputData loginInputData);

    void switchToSignUpView();

}
