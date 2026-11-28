package UseCase.History;

import Entity.History;
import Entity.SudokuGame;

import java.time.LocalDateTime;

public class SaveHistoryInteractor implements SaveHistoryInputBoundary {

    private final HistoryDataAccessInterface historyDAO;

    public SaveHistoryInteractor(HistoryDataAccessInterface historyDAO){
        this.historyDAO = historyDAO;
    }

    @Override
    public void execute(SudokuGame sudokuGame, String username) {
        if (sudokuGame.isCompleted() && !sudokuGame.isHistorySaved()) {
            History history = new History(
                    username,
                    sudokuGame.getDifficulty(),
                    LocalDateTime.now()
            );
            historyDAO.saveHistory(history);
            sudokuGame.markHistorySaved();
        }
    }
}