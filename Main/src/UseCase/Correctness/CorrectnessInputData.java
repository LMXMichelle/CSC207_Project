package UseCase.Correctness;

public class CorrectnessInputData {
    private final int row;
    private final int column;
    private final int input;

    public CorrectnessInputData(int Row, int Column, int Input){
        this.column = Column;
        this.row = Row;
        this.input = Input;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getInput() {
        return input;
    }
}
