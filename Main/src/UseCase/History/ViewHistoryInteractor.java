package UseCase.History;

import Entity.History;
import java.util.List;

public class ViewHistoryInteractor implements ViewHistoryInputBoundary{

    private final HistoryDataAccessInterface historyDAO;
    private final ViewHistoryOutputBoundary presenter;

    public ViewHistoryInteractor(HistoryDataAccessInterface historyDAO,
                                 ViewHistoryOutputBoundary presenter){
        this.historyDAO = historyDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(String username){
        List<History> history = historyDAO.getHistory(username);
        presenter.prepareView(history);
    }
}

