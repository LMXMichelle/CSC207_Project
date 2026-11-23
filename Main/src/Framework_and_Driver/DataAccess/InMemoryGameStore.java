package Framework_and_Driver.DataAccess;

import Entity.SudokuGame;

import UseCase.NewGame.GameStore;

public final class InMemoryGameStore implements GameStore {
    private volatile SudokuGame currentGame;

    public void save(SudokuGame game) {
        currentGame = game;
    }

    public SudokuGame getCurrentGame() {
        return currentGame;
    }
}
