package Framework_and_Driver.View;

import InterfaceAdapter.LoggedIn.LoggedInViewModel;
import InterfaceAdapter.LoggedIn.LoggedInViewModelListener;
import InterfaceAdapter.LoggedIn.LoggedInState;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoggedInView extends JPanel implements LoggedInViewModelListener {

    private final LoggedInViewModel loggedInViewModel;
    private final JLabel username;

    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addListener(this);

        final JLabel title = new JLabel("Logged In Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel usernameInfo = new JLabel("Currently logged in: ");
        username = new JLabel();
        username.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernameInfo);
        this.add(username);

        stateChanged();
    }

    @Override
    public void stateChanged() {
        LoggedInState state = loggedInViewModel.getState();
        username.setText(state.getUsername());
    }
}