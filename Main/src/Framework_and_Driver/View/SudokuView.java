package Framework_and_Driver.View;

import InterfaceAdapter.NewGame.NewGameController;
import InterfaceAdapter.NewGame.SudokuState;
import InterfaceAdapter.NewGame.SudokuViewModel;
import InterfaceAdapter.Correctness.CorrectnessController;

import java.awt.*;
import java.util.concurrent.ExecutionException;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

import javax.swing.*;
import javax.swing.text.*;

public final class SudokuView extends JPanel {
    private final JComboBox<String> difficulty =
            new JComboBox<>(new String[] {"easy", "medium", "hard"});
    private final JButton newGame = new JButton("New Game");
    private final JLabel status = new JLabel();
    private final JTextField[][] cells = new JTextField[9][9];
    private final SudokuViewModel viewModel;
    private final NewGameController controller;
    private final CorrectnessController correctnessController;
    // 用于区分“新棋盘”和“同一棋盘的加载/失败状态”，保留失败前的输入。
    private long displayedVersion = -1;
    private boolean rendering = false;

    public SudokuView(SudokuViewModel viewModel, NewGameController controller,
                      CorrectnessController correctnessController) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.correctnessController = correctnessController;
        setLayout(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.add(new JLabel("Difficulty:"));
        toolbar.add(difficulty);
        toolbar.add(newGame);
        add(toolbar, BorderLayout.NORTH);
        JPanel grid = new JPanel(new GridLayout(9, 9));
        grid.setPreferredSize(new Dimension(486, 486));
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 23));
                ((AbstractDocument) cell.getDocument()).setDocumentFilter(new DigitFilter());
                cell.setBorder(
                        BorderFactory.createMatteBorder(
                                r % 3 == 0 ? 2 : 1,
                                c % 3 == 0 ? 2 : 1,
                                r == 8 ? 2 : 0,
                                c == 8 ? 2 : 0,
                                Color.GRAY));
                final int row = r;
                final int col = c;

                cell.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        checkCell(cell, row, col);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        checkCell(cell, row, col);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        checkCell(cell, row, col);
                    }
                });
                cells[r][c] = cell;
                grid.add(cell);
            }
        }
        JPanel boardContainer = new JPanel(null) {
            @Override
            public void doLayout() {
                int cellSize = Math.min(getWidth(), getHeight()) / 9;
                int boardSize = cellSize * 9;

                int x = (getWidth() - boardSize) / 2;
                int y = (getHeight() - boardSize) / 2;
                grid.setBounds(x, y, boardSize, boardSize);
            }
        };

        boardContainer.setPreferredSize(grid.getPreferredSize());
        boardContainer.add(grid);
        add(boardContainer, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);
        newGame.addActionListener(event -> startNewGame());
        viewModel.addPropertyChangeListener(
                event -> {
                    SudokuState state = (SudokuState) event.getNewValue();
                    if (SwingUtilities.isEventDispatchThread()) render(state);
                    else SwingUtilities.invokeLater(() -> render(state));
                });
        render(viewModel.getState());
    }

    private void startNewGame() {
        if (viewModel.getState().isLoading()) return;
        String selected = (String) difficulty.getSelectedItem();
        // EDT 上立即禁用按钮，防止重复请求。
        viewModel.beginLoading();
        new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
                controller.execute(selected);
                return null;
            }

            protected void done() {
                try {
                    get();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    viewModel.showError("Loading was interrupted. Please retry.");
                } catch (ExecutionException e) {
                    viewModel.showError("Could not load a game. Please retry.");
                }
            }
        }.execute();
    }

    private void render(SudokuState state) {
        newGame.setEnabled(!state.isLoading());
        difficulty.setEnabled(!state.isLoading());
        status.setText(state.getMessage());
        int[][] board = state.getBoard();
        boolean newBoard = state.hasGame() && state.getGameVersion() != displayedVersion;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                JTextField cell = cells[r][c];
                int given = board[r][c];
                if (newBoard) cell.setText(given == 0 ? "" : String.valueOf(given));
                cell.setEditable(state.hasGame() && !state.isLoading() && given == 0);
                cell.setBackground(state.isIncorrect(r, c)
                        ? new Color(255, 220, 220)
                        : (given == 0 ? Color.WHITE : new Color(232, 237, 242)));
                cell.setFont(cell.getFont().deriveFont(given == 0 ? Font.PLAIN : Font.BOLD));
                cell.setForeground(state.isIncorrect(r, c)
                        ? Color.RED : UIManager.getColor("TextField.foreground"));
            }
        }
        if (newBoard) displayedVersion = state.getGameVersion();

        rendering = false;
    }

    private void checkCell(JTextField cell, int row, int column) {
        if (rendering || !cell.isEditable()) {
            return;
        }

        String text = cell.getText();

        if (text.isEmpty()) {
            viewModel.clearCorrectness(row, column);
        } else {
            int number = Integer.parseInt(text);
            correctnessController.execute(row, column, number);
        }
    }

    /** 允许空字符串或一个 1–9 的数字，包括粘贴和替换。 */
    private static final class DigitFilter extends DocumentFilter {
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs)
                throws BadLocationException {
            replace(fb, offset, 0, text, attrs);
        }

        public void replace(
                FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            String old = fb.getDocument().getText(0, fb.getDocument().getLength());
            String next =
                    old.substring(0, offset)
                            + (text == null ? "" : text)
                            + old.substring(offset + length);
            if (next.matches("[1-9]?")) fb.replace(offset, length, text, attrs);
        }
    }
}
