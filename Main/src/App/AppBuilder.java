package App;

import javax.swing.*;
import java.awt.*;

import App.FileUserDataAccessObject;
import Entity.UserFactory;
import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.LoggedIn.LoggedInViewModel;
import InterfaceAdapter.Login.LoginController;
import InterfaceAdapter.Login.LoginPresenter;
import InterfaceAdapter.Login.LoginViewModel;
import InterfaceAdapter.SignUp.SignUpController;
import InterfaceAdapter.SignUp.SignUpPresenter;
import InterfaceAdapter.SignUp.SignUpViewModel;
import UseCase.Login.LoginInputBoundary;
import UseCase.Login.LoginInteractor;
import UseCase.Login.LoginOutputBoundary;
import UseCase.SignUp.SignUpInputBoundary;
import UseCase.SignUp.SignUpInteractor;
import UseCase.SignUp.SignUpOutputBoundary;
import Framework_and_Driver.View.LoggedInView;
import Framework_and_Driver.View.SignUpView;
import Framework_and_Driver.View.LoginView;
import Framework_and_Driver.View.ViewManager;


import javax.swing.*;
import java.awt.*;

    public class AppBuilder {
        private final JPanel cardPanel = new JPanel();
        private final CardLayout cardLayout = new CardLayout();
        final UserFactory userFactory = new UserFactory();
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

        // set which data access implementation to use, can be any
        // of the classes from the data_access package

        // DAO version using local file storage
        final FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("users.csv", userFactory);

        private SignUpView signupView;
        private SignUpViewModel signupViewModel;
        private LoginViewModel loginViewModel;
        private LoggedInViewModel loggedInViewModel;
        private LoggedInView loggedInView;
        private LoginView loginView;

        public AppBuilder() {
            cardPanel.setLayout(cardLayout);
        }

        public AppBuilder addSignupView() {
            signupViewModel = new SignUpViewModel();
            signupView = new SignUpView(signupViewModel);
            cardPanel.add(signupView, SignUpViewModel.VIEW_NAME);
            return this;
        }

        public AppBuilder addLoginView() {
            loginViewModel = new LoginViewModel();
            loginView = new LoginView(loginViewModel);
            cardPanel.add(loginView, LoginViewModel.VIEW_NAME);
            return this;
        }

        public AppBuilder addLoggedInView() {
            loggedInViewModel = new LoggedInViewModel();
            loggedInView = new LoggedInView(loggedInViewModel);
            cardPanel.add(loggedInView, LoggedInViewModel.VIEW_NAME);
            return this;
        }

        public AppBuilder addSignupUseCase() {
            final SignUpOutputBoundary signupOutputBoundary = new SignUpPresenter(viewManagerModel,
                    signupViewModel, loginViewModel);
            final SignUpInputBoundary userSignupInteractor = new SignUpInteractor(
                    userDataAccessObject, signupOutputBoundary, userFactory);

            SignUpController controller = new SignUpController(userSignupInteractor);
            signupView.setSignupController(controller);
            return this;
        }

        public AppBuilder addLoginUseCase() {
            final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                    loggedInViewModel, loginViewModel);
            final LoginInputBoundary loginInteractor = new LoginInteractor(
                    userDataAccessObject, loginOutputBoundary);

            LoginController loginController = new LoginController(loginInteractor);
            loginView.setLoginController(loginController);
            return this;
        }

        public JFrame build() {
            final JFrame application = new JFrame("Sudoku207");
            application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            application.add(cardPanel);

            viewManagerModel.setState(SignUpViewModel.VIEW_NAME);
            viewManagerModel.notifyViewChanged();

            return application;
        }
}
