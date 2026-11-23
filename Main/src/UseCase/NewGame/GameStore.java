package UseCase.NewGame;

import Entity.SudokuGame;

/** 当前游戏状态的存储接口；以后其他 use case 可以复用。 */
public interface GameStore {
    void save(SudokuGame game);

    SudokuGame getCurrentGame();
}
