package InterfaceAdapter.History;

import Entity.History;
import UseCase.History.ViewHistoryOutputBoundary;

import java.util.List;

public class ViewHistoryPresenter implements ViewHistoryOutputBoundary {

    private final HistoryViewModel historyViewModel;

    public ViewHistoryPresenter(HistoryViewModel historyViewModel){
        this.historyViewModel = historyViewModel;
    }

    @Override
    public void prepareView(List<History> histories) {
        HistoryState currentState = historyViewModel.getState();
        currentState.setHistories(histories);
        historyViewModel.setState(currentState);
        historyViewModel.firePropertyChanged();
    }

}