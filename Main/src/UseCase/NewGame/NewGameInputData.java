package UseCase.NewGame;

public final class NewGameInputData {
    private final String difficulty;

    public NewGameInputData(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
