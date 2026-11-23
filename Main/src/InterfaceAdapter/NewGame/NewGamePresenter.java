package InterfaceAdapter.NewGame;

import UseCase.NewGame.NewGameOutputBoundary;
import UseCase.NewGame.NewGameOutputData;

public final class NewGamePresenter implements NewGameOutputBoundary {
    private final SudokuViewModel viewModel;

    public NewGamePresenter(SudokuViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void prepareSuccessView(NewGameOutputData output) {
        viewModel.showGame(output.getBoard(), output.getDifficulty());
    }

    public void prepareFailView(String message) {
        viewModel.showError(message);
    }
}
