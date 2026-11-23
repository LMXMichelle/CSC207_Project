package UseCase.NewGame;

import Entity.SudokuGame;

public interface NewGameDataAccessInterface {
    SudokuGame fetchGame(String difficulty) throws Exception;
}
