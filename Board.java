import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class Board extends JPanel {

    private final int CELL_SIZE = 43;
    private final int COVER_FOR_CELL = 29;
    private final int MARK_FOR_CELL = 29;
    private final int EMPTY_CELL = 0;
    private final int MINE_CELL = 9;
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;
    private final int N_ROWS = 16;
    private final int N_COLS = 16;
    private int[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img;
    private int allCells;
    private final JLabel statusBar;



    public Board(JLabel statusBar) {

        this.statusBar = statusBar;
        initBoard();
    }

    private void initBoard() {

        int BOARD_WIDTH = N_COLS * CELL_SIZE + 1;
        int BOARD_HEIGHT = N_ROWS * CELL_SIZE + 1;
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        int NUM_IMAGES = 13;
        img = new Image[NUM_IMAGES];

        for (int i = 0; i < NUM_IMAGES; i++) {
            var path = "src/resources/" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }

        addMouseListener(new MinesAdapter());
        newGame();
    }

    private void newGame() {

        int cell;
        var random = new Random();
        inGame = true;
        int n_MINES = 30;
        minesLeft = n_MINES;
        allCells = N_ROWS * N_COLS;
        field = new int[allCells];

        for (int i = 0; i < allCells; i++) {
            field[i] = COVER_FOR_CELL;
        }

        statusBar.setText(" You have " + minesLeft + " mines left to find.");

        int i = 0;
        while (i < n_MINES) {

            int position = (int) (allCells * random.nextDouble());
            if ((position < allCells) && (field[position] != COVERED_MINE_CELL)) {

                int current_col = position % N_COLS;
                field[position] = COVERED_MINE_CELL;
                i++;

                if (current_col > 0) {
                    cell = position - 1 - N_COLS;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position - 1;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }

                    cell = position + N_COLS - 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }

                cell = position - N_COLS;
                if (cell >= 0) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                cell = position + N_COLS;
                if (cell < allCells) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                if (current_col < (N_COLS - 1)) {
                    cell = position - N_COLS + 1;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + N_COLS + 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }
            }
        }
    }

    private void find_empty_cells(int j) {

        int current_col = j % N_COLS;
        int cell;

        /* if current column is valid */
        if (current_col > 0) {

            /* set cell to 61 */
            cell = j - N_COLS - 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            /* set cell to 67 */
            cell = j - 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            /* set cell to 83 */
            cell = j + N_COLS - 1;
            /* NOTE: allCells = nCols * nRows = 256 */

            /* if the cell is smaller total cells */
            if (cell < allCells) {

                /* if the field[cell] is bigger than a mine cell/9, subtract 29 from field[cell] */
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;

                    /* if field[112] is an empty cell/0, find more empty cells */
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

        /* set cell to 52 */
        cell = j - N_COLS;
        if (cell >= 0) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        /* set cell to 84 */
        cell = j + N_COLS;

        /* if cell smaller than 256 */
        if (cell < allCells) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        /* if col < 15 */
        if (current_col < (N_COLS - 1)) {
            /* set cell to 53 */
            cell = j - N_COLS + 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            /* set cell to 85 */
            cell = j + N_COLS + 1;
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            /* set cell to 69 */
            cell = j + 1;
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {

        int uncover = 0;
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {

                int cell = field[(i * N_COLS) + j];
                if (inGame && cell == MINE_CELL) {
                    inGame = false;
                }
                int DRAW_COVER = 10;
                int DRAW_MARK = 11;
                if (!inGame) {
                    if (cell == COVERED_MINE_CELL) {
                        cell = 9;
                    } else if (cell == MARKED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > COVERED_MINE_CELL) {
                        cell = 12;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                    }
                } else {
                    if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                        uncover++;
                    }
                }

                g.drawImage(img[cell], (j * CELL_SIZE),
                        (i * CELL_SIZE), this);
            }
        }

        if (uncover == 0 && inGame) {
            inGame = false;
            statusBar.setText("Game won");
            JOptionPane.showMessageDialog(null,"Well Done!!!");
        } else if (!inGame) {
            statusBar.setText("Game lost");
        }
    }

    private class MinesAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            boolean doRepaint = false;

            if (!inGame) {
                newGame();
                repaint();
            }

            if ((x < N_COLS * CELL_SIZE) && (y < N_ROWS * CELL_SIZE)) {

                /* --- RIGHT MOUSE BUTTON CLICKED --- */
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (field[(cRow * N_COLS) + cCol] > MINE_CELL) {
                        doRepaint = true;
                        if (field[(cRow * N_COLS) + cCol] <= COVERED_MINE_CELL) {
                            /* status bar update of mines left */
                            if (minesLeft >= 0) {
                                field[(cRow * N_COLS) + cCol] += MARK_FOR_CELL;
                                minesLeft--;
                                String msg = Integer.toString(minesLeft);
                                statusBar.setText(msg);
                            /* if no more marks for mines are left */
                            } else {
                                statusBar.setText("No marks left");
                            }
                        /* --- MARK ON CELL & update status bar with marks left --- */
                        } else {
                            field[(cRow * N_COLS) + cCol] -= MARK_FOR_CELL;
                            minesLeft++;
                            String msg = Integer.toString(minesLeft);
                            statusBar.setText(msg);
                        }
                    }

                /* --- LEFT MOUSE BUTTON CLICKED --- */
                } else {

                    /* if you uncover a mine */
                    if (field[(cRow * N_COLS) + cCol] > COVERED_MINE_CELL) {
                        return;
                    }

                    /* if field[n] between 9 and 67 then it is set to field[] - 29 and repainted */
                    if ((field[(cRow * N_COLS) + cCol] > MINE_CELL) && (field[(cRow * N_COLS) + cCol] < MARKED_MINE_CELL)) {
                        field[(cRow * N_COLS) + cCol] -= COVER_FOR_CELL;
                        doRepaint = true;

                        /* if field[n] is a mine then the game ends */
                        if (field[(cRow * N_COLS) + cCol] == MINE_CELL) {
                            inGame = false;
                        }

                        /* if field[n] is an empty cell, call method to find empty cells */
                        if (field[(cRow * N_COLS) + cCol] == EMPTY_CELL) {
                            find_empty_cells((cRow * N_COLS) + cCol);
                        }
                    }

                }

                if (doRepaint) {
                    repaint();
                }
            }
        }
    }

}
