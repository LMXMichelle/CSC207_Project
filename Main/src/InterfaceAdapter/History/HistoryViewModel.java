package InterfaceAdapter.History;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HistoryViewModel {

    public static final String TITLE_LABEL = "My History";
    public static final String BACK_BUTTON_LABEL = "Back";

    private HistoryState state = new HistoryState();

    private final PropertyChangeSupport support =
            new PropertyChangeSupport(this);

    public HistoryState getState() {
        return state;
    }

    public void setState(HistoryState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}