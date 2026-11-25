package InterfaceAdapter.NewGame;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/** 与 Swing 无关；所有界面线程调度由 View 处理。 */
public final class SudokuViewModel {
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private SudokuState state =
            new SudokuState(
                    new int[9][9], "", "Choose a difficulty and start a game.", false, false, 0);

    public synchronized SudokuState getState() {
        return state;
    }

    private void publish(SudokuState next) {
        synchronized (this) {
            state = next;
        }
        changes.firePropertyChange("state", null, next);
    }

    public void beginLoading() {
        SudokuState old = getState();
        publish(
                new SudokuState(
                        old.getBoard(),
                        old.getDifficulty(),
                        "Loading... The server may take a minute to start.",
                        true,
                        old.hasGame(),
                        old.getGameVersion()));
    }

    public void showGame(int[][] board, String difficulty) {
        publish(
                new SudokuState(
                        board,
                        difficulty,
                        "New " + difficulty + " game ready.",
                        false,
                        true,
                        getState().getGameVersion() + 1));
    }

    public void showError(String message) {
        SudokuState old = getState();
        publish(
                new SudokuState(
                        old.getBoard(),
                        old.getDifficulty(),
                        message,
                        false,
                        old.hasGame(),
                        old.getGameVersion()));
    }

    public void showCorrectness(int row, int column, boolean correct) {
        SudokuState current = getState();
        current.setIncorrect(row, column, !correct);
        publish(current);
    }

    public void clearCorrectness(int row, int column) {
        showCorrectness(row, column, true);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }
}
