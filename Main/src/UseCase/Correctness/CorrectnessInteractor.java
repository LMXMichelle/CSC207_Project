package UseCase.Correctness;

import Entity.SudokuGame;
import UseCase.NewGame.GameStore;

public class CorrectnessInteractor implements CorrectnessInputBoundary {
    private final GameStore gameStore;
    private final CorrectnessOutputBoundary presenter;

    public CorrectnessInteractor(GameStore GameStore, CorrectnessOutputBoundary Presenter){
        this.gameStore = GameStore;
        this.presenter = Presenter;
    }

    @Override
    public void execute(CorrectnessInputData correctnessInputData){
        SudokuGame CurrentGame = gameStore.getCurrentGame();

        int column = correctnessInputData.getColumn();
        int row = correctnessInputData.getRow();
        int input = correctnessInputData.getInput();

        boolean correct = CurrentGame.getSolution()[row][column] == input;

        CorrectnessOutputData correctnessOutputData = new CorrectnessOutputData(row, column, correct);

        presenter.prepareView(correctnessOutputData);
    }
}
