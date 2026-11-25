package InterfaceAdapter.NewGame;

import Entity.SudokuGame;

public final class SudokuState {
    private final int[][] board;
    private final long gameVersion;
    private final String difficulty, message;
    private final boolean loading, hasGame;
    private final boolean[][] incorrect;

    public SudokuState(
            int[][] board,
            String difficulty,
            String message,
            boolean loading,
            boolean hasGame,
            long gameVersion) {
        this.gameVersion = gameVersion;
        this.board = SudokuGame.copy(board);
        this.difficulty = difficulty;
        this.message = message;
        this.loading = loading;
        this.hasGame = hasGame;
        this.incorrect = new boolean[9][9];
    }

    public long getGameVersion() {
        return gameVersion;
    }

    public int[][] getBoard() {
        return SudokuGame.copy(board);
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getMessage() {
        return message;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean hasGame() {
        return hasGame;
    }

    public boolean isIncorrect(int row, int column) {
        return incorrect[row][column];
    }

    public void setIncorrect(int row, int column, boolean incorrect) {
        this.incorrect[row][column] = incorrect;
    }
}