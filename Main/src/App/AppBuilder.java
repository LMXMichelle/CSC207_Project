package App;

import Entity.UserFactory;

import Framework_and_Driver.DataAccess.InMemoryGameStore;
import Framework_and_Driver.DataAccess.SugokuDataAccessObject;
import Framework_and_Driver.View.LoggedInView;
import Framework_and_Driver.View.LoginView;
import Framework_and_Driver.View.SignUpView;
import Framework_and_Driver.View.SudokuView;
import Framework_and_Driver.View.ViewManager;
import Framework_and_Driver.DataAccess.FileHistoryDataAccessObject;
import Framework_and_Driver.View.HistoryView;

import InterfaceAdapter.LoggedIn.LoggedInViewModel;
import InterfaceAdapter.Login.LoginController;
import InterfaceAdapter.Login.LoginPresenter;
import InterfaceAdapter.Login.LoginViewModel;
import InterfaceAdapter.NewGame.NewGameController;
import InterfaceAdapter.NewGame.NewGamePresenter;
import InterfaceAdapter.NewGame.SudokuViewModel;
import InterfaceAdapter.SignUp.SignUpController;
import InterfaceAdapter.SignUp.SignUpPresenter;
import InterfaceAdapter.SignUp.SignUpViewModel;
import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.Correctness.CorrectnessController;
import InterfaceAdapter.Correctness.CorrectnessPresenter;
import InterfaceAdapter.History.HistoryViewModel;
import InterfaceAdapter.History.ViewHistoryController;
import InterfaceAdapter.History.ViewHistoryPresenter;

import UseCase.Login.LoginInputBoundary;
import UseCase.Login.LoginInteractor;
import UseCase.Login.LoginOutputBoundary;
import UseCase.NewGame.NewGameInteractor;
import UseCase.SignUp.SignUpInputBoundary;
import UseCase.SignUp.SignUpInteractor;
import UseCase.SignUp.SignUpOutputBoundary;
import UseCase.Correctness.CorrectnessInteractor;
import UseCase.History.SaveHistoryInteractor;
import UseCase.History.ViewHistoryInteractor;

import java.awt.*;

import javax.swing.*;

public class AppBuilder {
    private static final String SUDOKU_VIEW_NAME = "sudoku";
    private static final String HISTORY_VIEW_NAME = "history";

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);
    private final FileHistoryDataAccessObject historyDAO =
            new FileHistoryDataAccessObject("history.csv");

    // set which data access implementation to use, can be any
    // of the classes from the data_access package

    // DAO version using local file storage
    final FileUserDataAccessObject userDataAccessObject =
            new FileUserDataAccessObject("users.csv", userFactory);

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

    public AppBuilder addNewGameView() {
        SudokuViewModel sudokuViewModel = new SudokuViewModel();
        NewGamePresenter presenter = new NewGamePresenter(sudokuViewModel);
        InMemoryGameStore gameStore = new InMemoryGameStore();
        NewGameInteractor interactor =
                new NewGameInteractor(
                        new SugokuDataAccessObject(), gameStore, presenter);
        NewGameController controller = new NewGameController(interactor);
        CorrectnessPresenter correctnessPresenter =
                new CorrectnessPresenter(sudokuViewModel);

        SaveHistoryInteractor saveHistoryInteractor =
                new SaveHistoryInteractor(historyDAO);

        CorrectnessInteractor correctnessInteractor =
                new CorrectnessInteractor(gameStore, correctnessPresenter, saveHistoryInteractor);

        CorrectnessController correctnessController =
                new CorrectnessController(correctnessInteractor);
        SudokuView sudokuView = new SudokuView(sudokuViewModel, controller, correctnessController,
                loggedInViewModel);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(event -> showView(LoggedInViewModel.VIEW_NAME));
        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navigation.add(backButton);

        JPanel sudokuScreen = new JPanel(new BorderLayout());
        sudokuScreen.add(navigation, BorderLayout.NORTH);
        sudokuScreen.add(sudokuView, BorderLayout.CENTER);
        cardPanel.add(sudokuScreen, SUDOKU_VIEW_NAME);
        loggedInView.addNewGameButton(() -> showView(SUDOKU_VIEW_NAME));
        return this;
    }

    public AppBuilder addHistoryView() {
        HistoryViewModel historyViewModel = new HistoryViewModel();

        ViewHistoryPresenter presenter =
                new ViewHistoryPresenter(historyViewModel);

        ViewHistoryInteractor interactor =
                new ViewHistoryInteractor(historyDAO, presenter);

        ViewHistoryController controller =
                new ViewHistoryController(interactor);

        HistoryView historyView = new HistoryView(historyViewModel);

        cardPanel.add(historyView, HISTORY_VIEW_NAME);

        historyView.getBackButton().addActionListener(
                event -> showView(LoggedInViewModel.VIEW_NAME));

        loggedInView.addHistoryButton(() -> {
            controller.execute(loggedInViewModel.getState().getUsername());
            showView(HISTORY_VIEW_NAME);
        });

        return this;
    }

    private void showView(String viewName) {
        viewManagerModel.setState(viewName);
        viewManagerModel.notifyViewChanged();
    }

    public AppBuilder addSignupUseCase() {
        final SignUpOutputBoundary signupOutputBoundary =
                new SignUpPresenter(viewManagerModel, signupViewModel, loginViewModel);
        final SignUpInputBoundary userSignupInteractor =
                new SignUpInteractor(userDataAccessObject, signupOutputBoundary, userFactory);

        SignUpController controller = new SignUpController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary =
                new LoginPresenter(viewManagerModel, loggedInViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor =
                new LoginInteractor(userDataAccessObject, loginOutputBoundary);

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
