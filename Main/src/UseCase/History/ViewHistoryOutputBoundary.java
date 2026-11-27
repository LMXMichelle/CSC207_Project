package UseCase.History;

import Entity.History;
import java.util.List;

public interface ViewHistoryOutputBoundary {

    void prepareView(List<History> histories);

}