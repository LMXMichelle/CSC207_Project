package UseCase.Correctness;

import Entity.SudokuGame;
import UseCase.NewGame.GameStore;
import UseCase.History.SaveHistoryInputBoundary;

public class CorrectnessInteractor implements CorrectnessInputBoundary {
    private final GameStore gameStore;
    private final CorrectnessOutputBoundary presenter;
    private final SaveHistoryInputBoundary saveHistoryInteractor;

    public CorrectnessInteractor(GameStore GameStore, CorrectnessOutputBoundary Presenter,
                                 SaveHistoryInputBoundary saveHistoryInteractor){
        this.gameStore = GameStore;
        this.presenter = Presenter;
        this.saveHistoryInteractor = saveHistoryInteractor;
    }

    @Override
    public void execute(CorrectnessInputData correctnessInputData){
        SudokuGame CurrentGame = gameStore.getCurrentGame();

        int column = correctnessInputData.getColumn();
        int row = correctnessInputData.getRow();
        int input = correctnessInputData.getInput();
        String username = correctnessInputData.getUsername();

        CurrentGame.updateCell(row, column, input);

        boolean correct = input == 0
                || CurrentGame.getSolution()[row][column] == input;

        CorrectnessOutputData correctnessOutputData = new CorrectnessOutputData(row, column, correct);

        saveHistoryInteractor.execute(CurrentGame, username);

        presenter.prepareView(correctnessOutputData);
    }
}
