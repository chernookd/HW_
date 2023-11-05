package edu.project2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MazeDrawer extends JFrame {
    private final Maze maze;
    private final int cellSize;
    private final static int TIME_SHOW_MAZE = 5000;

    private final static int INDENT = 50;

    public JPanel panel;

    @SuppressWarnings("MagicNumber")
    public MazeDrawer(Maze maze, int cellSize) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.maze = maze;
        this.cellSize = cellSize;
        int width = maze.getCellArray()[0].length * cellSize + INDENT;
        int height = maze.getCellArray().length * cellSize + INDENT;
        setSize(new Dimension(width, height));

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                Cell[][] cellArray = maze.getCellArray();
                for (int i = 0; i < cellArray.length; i++) {
                    for (int j = 0; j < cellArray[i].length; j++) {
                        if (cellArray[i][j].getState() == Cell.State.WALL) {
                            Color wallColor = new Color(0, 0, 0);
                            graphics.setColor(wallColor);
                        }
                        if (cellArray[i][j].getState() == Cell.State.PASSAGE) {
                            Color passageColor = new Color(255, 235, 205);
                            graphics.setColor(passageColor);
                        }
                        if (cellArray[i][j].getState() == Cell.State.PATH) {
                            Color pathColor = new Color(0, 128, 0);
                            graphics.setColor(pathColor);
                        }
                        if (cellArray[i][j].getState() == Cell.State.END) {
                            Color pathColor = new Color(100, 0, 0);
                            graphics.setColor(pathColor);
                        }
                        if (cellArray[i][j].getState() == Cell.State.START) {
                            Color pathColor = new Color(0, 0, 100);
                            graphics.setColor(pathColor);
                        }
                        graphics.fillRect(cellSize * j, cellSize * i, cellSize, cellSize);
                    }
                }
            }
        };
        add(panel);
        this.setVisible(true);
        try {
            Thread.sleep(TIME_SHOW_MAZE);
        } catch (InterruptedException e) {
            System.exit(0);
        }

    }
}
