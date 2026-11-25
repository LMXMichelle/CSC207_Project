package UseCase.Correctness;

public class CorrectnessOutputData {
    private final int column;
    private final int row;
    private final boolean correct;

    public CorrectnessOutputData(int Row, int Column, boolean Correct){
        this.column = Column;
        this.row = Row;
        this.correct = Correct;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean isCorrect() {
        return correct;
    }
}
