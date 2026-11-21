package UseCase.Login;

public interface LoginOutputBoundary {

    void prepareSuccessView(LoginOutputData outputData);

    void prepareFailView(String errorMessage);

}
