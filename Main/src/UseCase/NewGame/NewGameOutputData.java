package UseCase.NewGame;

import Entity.SudokuGame;

/** 只向显示层传题目和难度；答案保留在 GameStore 中。 */
public final class NewGameOutputData {
    private final int[][] board;
    private final String difficulty;

    public NewGameOutputData(int[][] board, String difficulty) {
        this.board = SudokuGame.copy(board);
        this.difficulty = difficulty;
    }

    public int[][] getBoard() {
        return SudokuGame.copy(board);
    }

    public String getDifficulty() {
        return difficulty;
    }
}
