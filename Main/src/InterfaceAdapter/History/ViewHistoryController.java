package InterfaceAdapter.History;

import UseCase.History.ViewHistoryInputBoundary;

public class ViewHistoryController {

    private final ViewHistoryInputBoundary viewHistoryInteractor;

    public ViewHistoryController(ViewHistoryInputBoundary viewHistoryInteractor){
        this.viewHistoryInteractor = viewHistoryInteractor;
    }

    public void execute(String username) {
        viewHistoryInteractor.execute(username);
    }
}