package InterfaceAdapter.Login;

import UseCase.Login.LoginOutputBoundary;
import UseCase.Login.LoginOutputData;
import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.LoggedIn.LoggedInViewModel;
import InterfaceAdapter.LoggedIn.LoggedInState;
import InterfaceAdapter.SignUp.SignUpViewModel;

public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoggedInViewModel loggedInViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, update the loggedInViewModel's state
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername(response.getUsername());
        this.loggedInViewModel.notifyStateChanged();

        // and clear everything from the LoginViewModel's state
        this.loginViewModel.setState(new LoginState());
        this.loginViewModel.notifyStateChanged();

        // switch to the logged in view
        this.viewManagerModel.setState(LoggedInViewModel.VIEW_NAME);
        this.viewManagerModel.notifyViewChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.notifyStateChanged();
    }
    @Override
    public void switchToSignUpView() {
        viewManagerModel.setState(SignUpViewModel.VIEW_NAME);
        viewManagerModel.notifyViewChanged();
    }
}
