// NOTE: based on https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase ,
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete , and
// https://codereview.stackexchange.com/questions/215081/minesweeper-game-in-java-using-swing-gui

package ui;

import model.Board;
import model.*;
import persistence.FileLoader;
import persistence.FileSaver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.Box;

//the panel in which the game is rendered.
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener {
    private Board board;
    private JButton[][] buttons;
    private Container grid;
    FileSaver fileSaver;
    FileLoader fileLoader;
    private JPanel optionPanel;
    private JButton reset;
    private JButton save;

    // EFFECTS:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(Board b) {
        setPreferredSize(new Dimension(Board.WIDTH, Board.HEIGHT));
        setBackground(Color.GRAY);
        this.board = b;
        init();
        drawGrid();
    }

    private void init() {
        optionPanel = new JPanel();

        reset = new JButton("Reset");
        save = new JButton("Save");

        reset.addActionListener(this);
        save.addActionListener(this);

        optionPanel.add(reset);
        optionPanel.add(save);
        add(optionPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS:  draws the boxes onto grid
    private void drawGrid() {
        grid = new Container();
        grid.setLayout(new GridLayout(board.getBoard().length, board.getBoard().length));
        buttons = new JButton[board.getBoard().length][board.getBoard().length];
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard().length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(this);
                buttons[i][j].setPreferredSize(new Dimension(Board.WIDTH / board.getBoard().length,
                        Board.HEIGHT / board.getBoard().length));
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN,
                        Board.WIDTH / board.getBoard().length / 2));
                grid.add(buttons[i][j]);
            }
        }
        add(grid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (reset.equals(e.getSource())) {
            board = new Board(10);
            for (int i = 0; i < board.getBoard().length; i++) {
                for (int j = 0; j < board.getBoard().length; j++) {
                    buttons[i][j].setText("");
                    buttons[i][j].setEnabled(true);
                }
            }
        } else if (save.equals(e.getSource())) {
            saveGame();
            //ScorePanel.saveScores();
        }

        for (int x = 0; x < buttons.length; x++) {
            for (int y = 0; y < buttons.length; y++) {
                if (e.getSource() == buttons[x][y]) {
                    switch (board.getName(x, y)) {
                        case "mine":
                            printBoardSolution();
                            break;
                        default:
                            board.changeState(x, y);
                            buttons[x][y].setText((board.updateBoard(x, y)));
                            buttons[x][y].setEnabled(false);
                            break;
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Board puzzle and ScoreBoard sb from GAME_FILE, if that file exists;
    // otherwise calls init()
    private void loadGame() {
        try {
            board = FileLoader.readBoard("./data/Board.json");
            //update();
        } catch (Exception e) {
            System.out.println("No Saved Game \n");
            //init();
        }
    }

    /*
     * EFFECTS: saves Board puzzle and ScoreBoard sb to GAME_FILE
     */
    private void saveGame() {
        try {
            FileSaver.writeBoard(board, "./data/Board.json");
            System.out.println("Game Saved");
        } catch (Exception e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    /*
     * EFFECTS: prints out the solution for the game
     */
    private void printBoardSolution() {
        for (int x = 0; x < buttons.length; x++) {
            for (int y = 0; y < buttons.length; y++) {
                board.gameOver(x, y);
                board.changeState(x, y);
                switch (board.getName(x, y)) {
                    case "mine":
                        buttons[x][y].setForeground(Color.RED);
                        buttons[x][y].setText("X ");
                        break;
                    default:
                        buttons[x][y].setText((board.updateBoard(x, y)));
                        buttons[x][y].setEnabled(false);
                        break;
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS:  draws game over and replay messages onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        //centreString(GAME_OVER, g, fm, Board.HEIGHT / 2);
        //centreString(REPLAY, g, fm, Board.HEIGHT / 2 + 50);
        g.setColor(saved);
    }

    // MODIFIES: g
    // EFFECTS:  centres the string str horizontally onto g at vertical position y
    private void centreString(String str, Graphics g, FontMetrics fm, int y) {
        int width = fm.stringWidth(str);
        g.drawString(str, (Board.WIDTH - width) / 2, y);
    }
}

