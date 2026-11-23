package InterfaceAdapter.NewGame;

import UseCase.NewGame.NewGameInputBoundary;
import UseCase.NewGame.NewGameInputData;

public final class NewGameController {
    private final NewGameInputBoundary interactor;

    public NewGameController(NewGameInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String difficulty) {
        interactor.execute(new NewGameInputData(difficulty));
    }
}
