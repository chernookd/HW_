package edu.hw9.Task3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Maze {
    private final int height;
    private final int width;
    private final Cell[][] cellArray;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Maze(int height, int width, Cell[][] cellArray) {

        this.height = height;
        this.width = width;
        this.cellArray = cellArray;
    }

    public Cell[][] getCellArray() {
        return cellArray;
    }

    public boolean isValid(Cell cell) {
        return cell.getRow() < height  || cell.getRow()  > 0
                || cell.getColumn() < width || cell.getColumn() > 0;
    }
    /*public boolean isValid(Cell cell) {
        return cell.getRow() + 2 < height - 1 || cell.getRow() - 2 > 0
            || cell.getColumn() + 2 < width - 1 || cell.getColumn() - 2 > 0;
    }*/


    @SuppressWarnings("MagicNumber")
    public Cell getRandomPassageCell() {
        int randRow;
        int randColumn;
        do {
            Random random = new Random();
            randRow = random.nextInt(height - 4) + 2;
            randColumn = random.nextInt(width - 4) + 2;
        } while (this.cellArray[randRow][randColumn].getState() != Cell.State.PASSAGE);
        return this.cellArray[randRow][randColumn];
    }

    public List<Cell> getListPathCell() {
        return Arrays.stream(this.cellArray)
            .flatMap(Arrays::stream)
            .filter(cell -> cell.getState() == Cell.State.PATH)
            .collect(Collectors.toList());
    }

    public List<Cell> getNeighbors(Cell current) {
        List<Cell> neighbors = new ArrayList<>();
        if (current.getRow() + 1 < this.getHeight()
            && cellArray[current.getRow() + 1][current.getColumn()].getState() != Cell.State.WALL
            && !cellArray[current.getRow() + 1][current.getColumn()].isVisited()) {
            neighbors.add(cellArray[current.getRow() + 1][current.getColumn()]);
        }
        if (current.getRow() - 1 >= 0
            && cellArray[current.getRow() - 1][current.getColumn()].getState() != Cell.State.WALL
            && !cellArray[current.getRow() - 1][current.getColumn()].isVisited()) {
            neighbors.add(cellArray[current.getRow() - 1][current.getColumn()]);
        }
        if (current.getColumn() + 1 < this.getWidth()
            && cellArray[current.getRow()][current.getColumn() + 1].getState() != Cell.State.WALL
            && !cellArray[current.getRow()][current.getColumn() + 1].isVisited()) {
            neighbors.add(cellArray[current.getRow()][current.getColumn() + 1]);
        }
        if (current.getColumn() - 1 >= 0
            && cellArray[current.getRow()][current.getColumn() - 1].getState() != Cell.State.WALL
            && cellArray[current.getRow()][current.getColumn() - 1].isVisited()) {
            neighbors.add(cellArray[current.getRow()][current.getColumn() - 1]);
        }
        return neighbors;
    }
}
