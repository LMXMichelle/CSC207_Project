package UseCase.NewGame;

public interface NewGameOutputBoundary {
    void prepareSuccessView(NewGameOutputData output);

    void prepareFailView(String message);
}
