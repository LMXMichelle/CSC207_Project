package InterfaceAdapter.SignUp;

import UseCase.SignUp.SignUpInputBoundary;
import UseCase.SignUp.SignUpInputData;

public class SignUpController {

    private final SignUpInputBoundary userSignUpInteractor;

    public SignUpController(SignUpInputBoundary userSignUpInteractor){
        this.userSignUpInteractor = userSignUpInteractor;
    }

    void execute(String username, String pw, String repeatpw){
        final SignUpInputData signUpInputData = new SignUpInputData(username, pw, repeatpw);
        userSignUpInteractor.execute(signUpInputData);
    }
    void switchToLoginView() {
        userSignUpInteractor.switchToLoginView();
    }
}
