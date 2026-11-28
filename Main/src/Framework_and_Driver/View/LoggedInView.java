package Framework_and_Driver.View;

import InterfaceAdapter.LoggedIn.LoggedInState;
import InterfaceAdapter.LoggedIn.LoggedInViewModel;
import InterfaceAdapter.LoggedIn.LoggedInViewModelListener;
import InterfaceAdapter.History.ViewHistoryController;

import java.awt.*;

import javax.swing.*;

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

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernameInfo);
        this.add(username);

        stateChanged();
    }

    public void addNewGameButton(Runnable openSudoku) {
        JButton newGameButton = new JButton("Play Sudoku");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.addActionListener(event -> openSudoku.run());
        add(newGameButton);
    }

    public void addHistoryButton(Runnable openHistory){
        JButton HistoryButton = new JButton("My History");
        HistoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        HistoryButton.addActionListener(event -> openHistory.run());
        add(HistoryButton);
    }

    @Override
    public void stateChanged() {
        LoggedInState state = loggedInViewModel.getState();
        username.setText(state.getUsername());
    }
}
