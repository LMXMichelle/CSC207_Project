package UseCase.SignUp;

import Entity.User;
import Entity.UserFactory;

public class SignUpInteractor implements SignUpInputBoundary{
    private final SignUpUserDataAccessInterface userDAO;
    private final SignUpOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public SignUpInteractor(SignUpUserDataAccessInterface signupDataAccessInterface,
                            SignUpOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.userDAO = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignUpInputData signupInputData) {
        //Question to ask: Is it too long? Do I need to break down
        if ("".equals(signupInputData.getUsername())) {
            userPresenter.prepareFailView("Username cannot be empty");
        }
        else if (userDAO.existsByName(signupInputData.getUsername())) {
            userPresenter.prepareFailView("User already exists");
        }
        else if ("".equals(signupInputData.getPw())) {
            userPresenter.prepareFailView("Password cannot be empty");
        }
        else if (signupInputData.getPw().length() < 6) {
            userPresenter.prepareFailView("Password has to contain at least 6 letters");
        }
        else if (!signupInputData.getPw().matches(".*[a-z].*")) {
            userPresenter.prepareFailView("Password has to contain at least one lower case letter");
        }
        else if (!signupInputData.getPw().matches(".*[A-Z].*")) {
            userPresenter.prepareFailView("Password has to contain at least one upper case letter");
        }
        else if (!signupInputData.getPw().matches(".*[0-9].*")) {
            userPresenter.prepareFailView("Password has to contain at least one number");
        }
        else if (!signupInputData.getPw().equals(signupInputData.getRepeatpw())) {
            userPresenter.prepareFailView("Passwords don't match");
        }
        else {
            final User user = userFactory.create(signupInputData.getUsername(),
                    signupInputData.getPw());
            userDAO.save(user);

            final SignUpOutputData signupOutputData = new SignUpOutputData(user.getName());
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
