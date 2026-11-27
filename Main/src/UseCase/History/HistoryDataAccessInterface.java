package UseCase.History;

import Entity.History;
import java.util.List;

public interface HistoryDataAccessInterface {

    void saveHistory(History history);

    List<History> getHistory(String username);
}
