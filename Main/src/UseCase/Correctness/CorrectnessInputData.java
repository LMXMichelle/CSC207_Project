package UseCase.Correctness;

public class CorrectnessInputData {
    private final int row;
    private final int column;
    private final int input;
    private final String username;

    public CorrectnessInputData(int Row, int Column, int Input, String username){
        this.column = Column;
        this.row = Row;
        this.input = Input;
        this.username = username;
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

    public String getUsername() { return username; }
}
