package Framework_and_Driver.View;

import InterfaceAdapter.SignUp.SignUpViewModel;
import InterfaceAdapter.SignUp.SignUpController;
import InterfaceAdapter.SignUp.SignUpState;
import InterfaceAdapter.SignUp.SignUpModelListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class SignUpView extends JPanel implements ActionListener, SignUpModelListener {

    private final SignUpViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(21);
    private final JPasswordField passwordInputField = new JPasswordField(21);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(21);
    private SignUpController signupController = null;
    private final JLabel errorLabel = new JLabel("");
    private final JButton signUp;
    private final JButton cancel;
    private final JButton toLogin;

    public SignUpView(SignUpViewModel signupViewModel) {
        errorLabel.setForeground(Color.RED);
        this.signupViewModel = signupViewModel;
        signupViewModel.addListener(this);

        final JLabel title = new JLabel(SignUpViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel(SignUpViewModel.USERNAME_LABEL), usernameInputField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(SignUpViewModel.PW_LABEL), passwordInputField);
        final LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                new JLabel(SignUpViewModel.REPEAT_PW_LABEL), repeatPasswordInputField);

        final JPanel buttons = new JPanel();
        toLogin = new JButton(SignUpViewModel.TO_LOGIN_BUTTON_LABEL);
        buttons.add(toLogin);
        signUp = new JButton(SignUpViewModel.SIGNUP_BUTTON_LABEL);
        buttons.add(signUp);
        cancel = new JButton(SignUpViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);

        signUp.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            final SignUpState currentState = signupViewModel.getState();

                            signupController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword(),
                                    currentState.getRepeatPassword()
                            );
                        }
                    }
                }
        );

        toLogin.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        signupController.switchToLoginView();
                    }
                }
        );

        cancel.addActionListener(this);

        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(243, 232, 255));
        buttons.setOpaque(false);
        usernameInfo.setOpaque(false);
        passwordInfo.setOpaque(false);
        repeatPasswordInfo.setOpaque(false);

        this.add(title);
        this.add(usernameInfo);
        this.add(errorLabel);

        this.add(passwordInfo);
        this.add(repeatPasswordInfo);
        this.add(buttons);
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignUpState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signupViewModel.setState(currentState);
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
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignUpState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
    }

    private void addRepeatPasswordListener() {
        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignUpState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Why cancel? Sign up!");
    }

    @Override
    public void stateChanged() {
        if (signupViewModel.getState().getUsernameError() != null) {
            final SignUpState state = signupViewModel.getState();

            errorLabel.setText(
                    state.getUsernameError() == null ? "" : state.getUsernameError()
            );
        }
    }

    public void setSignupController(SignUpController controller) {
        this.signupController = controller;
    }
}
