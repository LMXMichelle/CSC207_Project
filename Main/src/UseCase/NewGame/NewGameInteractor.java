package UseCase.NewGame;

import Entity.SudokuGame;

import java.util.Arrays;
import java.util.Locale;

public final class NewGameInteractor implements NewGameInputBoundary {
    private final NewGameDataAccessInterface dataAccess;
    private final GameStore gameStore;
    private final NewGameOutputBoundary presenter;

    public NewGameInteractor(
            NewGameDataAccessInterface dataAccess,
            GameStore gameStore,
            NewGameOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.gameStore = gameStore;
        this.presenter = presenter;
    }

    @Override
    public void execute(NewGameInputData input) {
        String difficulty =
                input == null || input.getDifficulty() == null
                        ? ""
                        : input.getDifficulty().trim().toLowerCase(Locale.ROOT);
        if (!Arrays.asList("easy", "medium", "hard").contains(difficulty)) {
            presenter.prepareFailView("Please choose easy, medium or hard.");
            return;
        }
        SudokuGame game;
        try {
            game = dataAccess.fetchGame(difficulty);
            if (game == null) throw new IllegalStateException("Missing game");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            presenter.prepareFailView("Loading was interrupted. Please retry.");
            return;
        } catch (Exception e) {
            // 不把 HTTP/JSON 等底层实现细节暴露到用户界面。
            presenter.prepareFailView("Could not load a game. Check your connection and retry.");
            return;
        }
        // 题目和答案均获取成功后才替换旧游戏。
        gameStore.save(game);
        presenter.prepareSuccessView(
                new NewGameOutputData(game.getInitialBoard(), game.getDifficulty()));
    }
}
