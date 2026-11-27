package UseCase.History;

import Entity.SudokuGame;

public interface SaveHistoryInputBoundary {

    void execute(SudokuGame sudokuGame, String username);
}
