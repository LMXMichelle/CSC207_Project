package Framework_and_Driver.View;

import Entity.History;
import InterfaceAdapter.History.HistoryViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.format.DateTimeFormatter;

public class HistoryView extends JPanel implements PropertyChangeListener {

    private final HistoryViewModel historyViewModel;

    private final JButton backButton =
            new JButton(HistoryViewModel.BACK_BUTTON_LABEL);

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                    new String[]{"Difficulty", "Completed At"}, 0);

    private final JTable historyTable = new JTable(tableModel);

    public HistoryView(HistoryViewModel historyViewModel) {
        this.historyViewModel = historyViewModel;

        historyViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        JLabel title = new JLabel(
                HistoryViewModel.TITLE_LABEL,
                SwingConstants.CENTER);

        historyTable.setEnabled(false);

        add(title, BorderLayout.NORTH);
        add(new JScrollPane(historyTable), BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        tableModel.setRowCount(0);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (History history : historyViewModel.getState().getHistories()) {
            tableModel.addRow(new Object[]{
                    history.getDifficulty(),
                    history.getCompletedTime().format(formatter)
            });
        }
    }

    public JButton getBackButton() {
        return backButton;
    }
}