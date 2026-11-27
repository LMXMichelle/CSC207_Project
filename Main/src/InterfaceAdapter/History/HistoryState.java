package InterfaceAdapter.History;

import Entity.History;

import java.util.List;
import java.util.ArrayList;

public class HistoryState {
    private List<History> histories = new ArrayList<History>();

    public HistoryState(){
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories){
        this.histories = histories;
    }
}
