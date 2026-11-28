package Entity;

/** 数独数据及约束；完全不依赖 Swing、HTTP、Gson。 */
public final class SudokuGame {
    private final int[][] initialBoard;
    private final int[][] solution;
    private final String difficulty;
    private final int[][] currentBoard;
    private boolean historySaved = false;

    public SudokuGame(int[][] board, int[][] solution, String difficulty) {
        validateShape(board);
        validateShape(solution);
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int given = board[r][c];
                int answer = solution[r][c];
                if (given < 0
                        || given > 9
                        || answer < 1
                        || answer > 9
                        || (given != 0 && given != answer)) {
                    throw new IllegalArgumentException("Invalid puzzle or mismatched solution");
                }
            }
        }
        // 每一行、列、宫都必须是 1–9。
        for (int i = 0; i < 9; i++) {
            boolean[] row = new boolean[10], col = new boolean[10], box = new boolean[10];
            for (int j = 0; j < 9; j++) {
                int a = solution[i][j], b = solution[j][i];
                int k = solution[(i / 3) * 3 + j / 3][(i % 3) * 3 + j % 3];
                if (row[a] || col[b] || box[k]) {
                    throw new IllegalArgumentException("Invalid Sudoku solution");
                }
                row[a] = true;
                col[b] = true;
                box[k] = true;
            }
        }
        this.initialBoard = copy(board);
        this.solution = copy(solution);
        this.difficulty = difficulty;
        this.currentBoard = copy(board);
    }

    private static void validateShape(int[][] board) {
        if (board == null || board.length != 9)
            throw new IllegalArgumentException("Expected 9 rows");
        for (int[] row : board) {
            if (row == null || row.length != 9)
                throw new IllegalArgumentException("Expected 9 columns");
        }
    }

    public static int[][] copy(int[][] board) {
        int[][] result = new int[board.length][];
        for (int i = 0; i < board.length; i++) result[i] = board[i].clone();
        return result;
    }

    public int[][] getInitialBoard() {
        return copy(initialBoard);
    }

    public int[][] getSolution() {
        return copy(solution);
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean isHistorySaved() {
        return historySaved;
    }

    public void updateCell(int row, int column, int input){
        if (input < 0 || input > 9 ||
                row < 0 || row > 8 ||
                column < 0 || column > 8){
            throw new IllegalArgumentException("Invalid cell or input");
        }
        if (initialBoard[row][column] != 0){
            return;
        }
        currentBoard[row][column] = input;

    };

    public boolean isCompleted() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (currentBoard[row][column] != solution[row][column]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void markHistorySaved() {
        this.historySaved = true;
    }
}
