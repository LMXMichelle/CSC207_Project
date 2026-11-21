package Framework_and_Driver.View;

import InterfaceAdapter.Login.LoginViewModel;
import InterfaceAdapter.Login.LoginViewModelListener;
import InterfaceAdapter.Login.LoginController;
import InterfaceAdapter.Login.LoginState;
import Framework_and_Driver.View.LabelTextPanel;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JPanel implements ActionListener, LoginViewModelListener {

    private final LoginViewModel loginViewModel;

    private final JTextField usernameInputField = new JTextField(21);
    private final JLabel loginErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(21);

    private final JButton logIn;
    private final JButton cancel;
    private final JButton signUpButton;
    private LoginController loginController = null;

    public LoginView(LoginViewModel loginViewModel) {

        this.loginViewModel = loginViewModel;
        this.loginViewModel.addListener(this);

        final JLabel title = new JLabel("Sudoku207 Login");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Enter Username"), usernameInputField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Enter Password"), passwordInputField);

        final JPanel buttons = new JPanel();
        logIn = new JButton("Log in");
        buttons.add(logIn);
        cancel = new JButton("Cancel");
        buttons.add(cancel);
        signUpButton = new JButton("Sign Up");
        buttons.add(signUpButton);
        loginErrorField.setForeground(Color.RED);

        logIn.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logIn)) {
                            final LoginState currentState = loginViewModel.getState();

                            loginController.execute(
                                    currentState.getUsername(),
                                    currentState.getPw()
                            );
                        }
                    }
                }
        );

        cancel.addActionListener(this);

        signUpButton.addActionListener(
                evt -> loginController.switchToSignUpView()
        );

        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        this.setBackground(new Color(255, 250, 205));
        buttons.setOpaque(false);
        usernameInfo.setOpaque(false);
        passwordInfo.setOpaque(false);

        this.add(title);
        this.add(usernameInfo);
        this.add(loginErrorField);
        this.add(passwordInfo);
        this.add(buttons);
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void stateChanged() {
        setFields(loginViewModel.getState());
        loginErrorField.setText(loginViewModel.getState().getLoginError());
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}

